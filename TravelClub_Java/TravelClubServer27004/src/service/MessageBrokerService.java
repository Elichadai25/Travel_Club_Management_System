package service;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * MessageBrokerService - uses Apache ActiveMQ as the message broker.
 * Queue: "travel.notifications"
 *
 * Falls back to in-memory BlockingQueue if ActiveMQ broker is not running,
 * so the application works in both modes.
 *
 * To enable real ActiveMQ:
 *   1. Download activemq-all-5.x.x.jar and add to project classpath
 *   2. Start ActiveMQ broker: activemq start  (default port 61616)
 *   3. Rebuild and run — broker mode activates automatically
 */
public class MessageBrokerService {

    public static final String QUEUE_NAME   = "travel.notifications";
    public static final String BROKER_URL   = "tcp://localhost:61616";

    private static MessageBrokerService instance;

    // JMS objects (used when ActiveMQ is available)
    private Connection    jmsConnection;
    private Session       jmsSession;
    private MessageProducer jmsProducer;
    private boolean       activeMQAvailable = false;

    // Fallback in-memory queue
    private static final BlockingQueue<String> fallbackQueue = new LinkedBlockingQueue<>();

    private MessageBrokerService() {
        tryConnectActiveMQ();
    }

    public static MessageBrokerService getInstance() {
        if (instance == null) instance = new MessageBrokerService();
        return instance;
    }

    private void tryConnectActiveMQ() {
        try {
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BROKER_URL);
            factory.setTrustAllPackages(true);
            jmsConnection = factory.createConnection();
            jmsConnection.start();
            jmsSession  = jmsConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination dest = jmsSession.createQueue(QUEUE_NAME);
            jmsProducer = (service.MessageProducer) jmsSession.createProducer(dest);
            jmsProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            activeMQAvailable = true;
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║  [ActiveMQ] Connected to broker          ║");
            System.out.println("║  URL   : " + BROKER_URL);
            System.out.println("║  Queue : " + QUEUE_NAME);
            System.out.println("╚══════════════════════════════════════════╝");
        } catch (Exception e) {
            activeMQAvailable = false;
            System.out.println("[Broker] ActiveMQ not available (" + e.getMessage() + ")");
            System.out.println("[Broker] Using in-memory BlockingQueue fallback. Queue: " + QUEUE_NAME);
        }
    }

    /**
     * Publishes a notification event to the broker.
     * Format: "TYPE|userId|message"
     */
    public void sendNotificationEvent(String type, int userId, String message) {
        String payload = type + "|" + userId + "|" + message;
        if (activeMQAvailable) {
            try {
                TextMessage msg = (TextMessage) jmsSession.createTextMessage(payload);
                msg.setStringProperty("type",   type);
                msg.setIntProperty("userId",    userId);
                jmsProducer.send(msg);
                System.out.println("[ActiveMQ] Published → " + QUEUE_NAME + " | " + payload);
                return;
            } catch (Exception e) {
                System.err.println("[ActiveMQ] Publish failed, using fallback: " + e.getMessage());
                activeMQAvailable = false;
            }
        }
        // Fallback
        try {
            fallbackQueue.put(payload);
            System.out.println("[Broker] Published (fallback) → " + QUEUE_NAME + " | " + payload);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean isActiveMQAvailable() { return activeMQAvailable; }
    public Session getJmsSession()       { return jmsSession; }
    public String  getBrokerUrl()        { return BROKER_URL; }

    /** Used by fallback consumer */
    public static BlockingQueue<String> getFallbackQueue() { return fallbackQueue; }

    public void shutdown() {
        try {
            if (jmsProducer  != null) jmsProducer.close();
            if (jmsSession   != null) jmsSession.close();
            if (jmsConnection != null) jmsConnection.close();
            System.out.println("[ActiveMQ] Connection closed.");
        } catch (Exception ignored) {}
    }
}
