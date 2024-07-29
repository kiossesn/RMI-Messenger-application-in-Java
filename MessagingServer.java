package Server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class MessagingServer {

    public static void main(String args[]) {
        int port=Integer.parseInt(args[0]);
        try {
            RemoteMessenger stub = new RemoteMessenger();
            // create the RMI registry on port 5000
            Registry rmiRegistry = LocateRegistry.createRegistry(port);
            // path to access is rmi://localhost:5000/messenger
            rmiRegistry.rebind("messenger", stub);
            System.out.println("RMIMessenger.RMIMessengerSerialized.Server is ready");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

