package rmi;

import service.Impl.TravelClubServiceImpl;
import util.HibernateUtil;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerMain {
    public static final int PORT = 5000;
    public static final String SERVICE_NAME = "TravelClubService";

    public static void main(String[] args) {
        try {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");

            HibernateUtil.getSessionFactory();
            System.out.println("[Server] Hibernate initialized successfully.");

            ITravelClubService service = (ITravelClubService) new TravelClubServiceImpl();

            Registry registry;
            try {
                registry = LocateRegistry.createRegistry(PORT);
            } catch (Exception e) {
                registry = LocateRegistry.getRegistry(PORT);
            }
            registry.rebind(SERVICE_NAME, service);

            System.out.println("[Server] TravelClub RMI Server started on port " + PORT);
            System.out.println("[Server] Service bound as: " + SERVICE_NAME);
            System.out.println("[Server] Waiting for client connections... (Press Ctrl+C to stop)");

            Object lock = new Object();
            synchronized (lock) { lock.wait(); }

        } catch (InterruptedException ie) {
            System.out.println("[Server] Server interrupted, shutting down.");
            HibernateUtil.shutdown();
        } catch (Exception e) {
            System.err.println("[Server] Failed to start: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
