package rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServiceConnector {
    private static ITravelClubService service;
    private static final String HOST = "localhost";
    private static final int PORT = 5000;
    private static final String SERVICE_NAME = "TravelClubService";

    public static ITravelClubService getService() {
        if (service == null) {
            try {
                Registry registry = LocateRegistry.getRegistry(HOST, PORT);
                service = (ITravelClubService) registry.lookup(SERVICE_NAME);
                System.out.println("[Client] Connected to TravelClub server.");
            } catch (Exception e) {
                // Do NOT cache null — allow retry on next call
                throw new RuntimeException("Cannot connect to server on " + HOST + ":" + PORT
                        + ". Please make sure the server is running first.\n" + e.getMessage(), e);
            }
        }
        return service;
    }

    public static void reset() {
        service = null;
    }
}
