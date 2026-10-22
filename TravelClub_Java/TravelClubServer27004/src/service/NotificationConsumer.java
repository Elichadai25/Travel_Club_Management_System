package service;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import java.util.concurrent.BlockingQueue;

/**
 * NotificationConsumer - consumes messages from the ActiveMQ broker queue.
 * Falls back to in-memory BlockingQueue if ActiveMQ is not available.
 *
 * Listens on queue: "travel.notifications"
 */
public class NotificationConsumer implements MessageListener, Runnable {

    private volatile boolean running = true;
    private Thread fallbackThread;

    // JMS objects for ActiveMQ mode
    private Connection       jmsConnection;
    private Session          jmsSession;
    private javax.jms.MessageConsumer jmsConsumer;
    private boolean          activeMQMode = false;

    public void start() {
        MessageBrokerService broker = MessageBrokerService.getInstance();

        if (broker.isActiveMQAvailable()) {
            startActiveMQConsumer();
        } else {
            startFallbackConsumer();
        }
    }

    /** Real ActiveMQ JMS consumer using MessageListener (async, event-driven) */
    private void startActiveMQConsumer() {
        try {
            ActiveMQConnectionFactory factory =
                new ActiveMQConnectionFactory(MessageBrokerService.BROKER_URL);
            factory.setTrustAllPackages(true);
            jmsConnection = factory.createConnection();
            jmsConnection.start();
            jmsSession  = jmsConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination dest = jmsSession.createQueue(MessageBrokerService.QUEUE_NAME);
            jmsConsumer = jmsSession.createConsumer(dest);
            jmsConsumer.setMessageListener(this);  // async callback
            activeMQMode = true;
            System.out.println("[ActiveMQ Consumer] Listening on queue: "
                + MessageBrokerService.QUEUE_NAME
                + " @ " + MessageBrokerService.BROKER_URL);
        } catch (Exception e) {
            System.err.println("[ActiveMQ Consumer] Failed to start: " + e.getMessage());
            startFallbackConsumer();
        }
    }

    /** Fallback: poll in-memory BlockingQueue in a background thread */
    private void startFallbackConsumer() {
        fallbackThread = new Thread(this, "NotificationConsumer-Fallback");
        fallbackThread.setDaemon(true);
        fallbackThread.start();
        System.out.println("[Consumer] Notification consumer started (fallback mode). Queue: "
            + MessageBrokerService.QUEUE_NAME);
    }

    /** Called by ActiveMQ when a message arrives (async) */
    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                String payload = ((TextMessage) message).getText();
                processMessage(payload);
            }
        } catch (Exception e) {
            System.err.println("[ActiveMQ Consumer] Error processing message: " + e.getMessage());
        }
    }

    /** Fallback thread: blocks on queue.take() */
    @Override
    public void run() {
        BlockingQueue<String> queue = MessageBrokerService.getFallbackQueue();
        while (running) {
            try {
                String payload = queue.take();
                processMessage(payload);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void processMessage(String payload) {
        String[] parts   = payload.split("\\|", 3);
        String type      = parts.length > 0 ? parts[0] : "UNKNOWN";
        String userId    = parts.length > 1 ? parts[1] : "?";
        String message   = parts.length > 2 ? parts[2] : payload;
        String mode      = activeMQMode ? "ActiveMQ" : "Fallback";

        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║  [NOTIFICATION EVENT — " + mode + "]");
        System.out.println("║  Queue   : " + MessageBrokerService.QUEUE_NAME);
        System.out.println("║  Type    : " + type);
        System.out.println("║  User ID : " + userId);
        System.out.println("║  Message : " + (message.length() > 60
                           ? message.substring(0, 60) + "..." : message));
        System.out.println("╚══════════════════════════════════════════════╝");
    }

    public void stop() {
        running = false;
        try {
            if (jmsConsumer  != null) jmsConsumer.close();
            if (jmsSession   != null) jmsSession.close();
            if (jmsConnection != null) jmsConnection.close();
        } catch (Exception ignored) {}
        if (fallbackThread != null) fallbackThread.interrupt();
        System.out.println("[Consumer] Notification consumer stopped.");
    }
}
