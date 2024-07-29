package Client;
import Server.*;
import Other.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class MessagingClient {
  public static MessengerInt stub;

    public static void main(String args[]) throws RemoteException {
        String ip= args[0];
        int port = Integer.parseInt(args[1]);
        int FN_ID= Integer.parseInt(args[2]);

        try {
            // connect to the RMI registry
            Registry rmiRegistry = LocateRegistry.getRegistry(port);
            // get reference for remote object
            stub = (MessengerInt) rmiRegistry.lookup("messenger");
            // Current client account
            //account=new Other.Account();
        } catch (Exception e) {
            System.out.println(e);
        }

        switch (FN_ID) {
            case 1:
                createAccount(args[3]);
                break;
            case 2:
                showAccounts(Integer.parseInt(args[3]));
                break;
            case 3:
                sendMessage(Integer.parseInt(args[3]), args[4], args[5]);
                break;
            case 4:
                showInbox(Integer.parseInt(args[3]));
                break;
            case 5:
                readMessage(Integer.parseInt(args[3]), Integer.parseInt(args[4]));
                break;
            case 6:
                deleteMessage(Integer.parseInt(args[3]), Integer.parseInt(args[4]));
                break;
            default: System.out.println("Invalid Function Id");
        }

    }
    public static void createAccount(String username) throws RemoteException {
        // Create account and get the authToken
        int authToken= stub.createAccounts(username);

        // Username already exists if createAccount returns -1
        if (authToken == -1) System.out.println("Sorry, the user already exists");

        // Username is invalid if createAccount returns -2
        else if (authToken == -2) System.out.println("Invalid Username");
        else {
            System.out.println(authToken);
        }
    }

    public static void showAccounts(int authToken) throws RemoteException {
        if(stub.searchAuthToken(authToken)==true) {
            String[] usernames = stub.showAccountss();
            int i = 0;
            for (String s : usernames) {
                i++;
                if (s != null) System.out.println(i + ". " + s);
            }
        }
        else System.out.println("Invalid Auth Token");
    }

    public static void sendMessage(int authToken, String recipient, String message_body) throws RemoteException {
        if(stub.searchAuthToken(authToken)==true) {
            // Find account by auth token
            Account sender = stub.searchAccounts(authToken);
            Account receiver;
            while (true) {
                // Specify receiver via his username
                receiver = stub.findAccounts(recipient);

                // Username found
                if (receiver != null) {
                    break;
                }
                else System.out.println("User does not exist");
            }
            // Send message
            stub.sendMessages(sender, authToken, receiver, message_body);

            System.out.println("OK");
        }
        else System.out.println("Invalid Auth Token");
    }

    public static void showInbox(int authToken) throws RemoteException {
        if(stub.searchAuthToken(authToken)==true) {
            // Get inbox by showInbox function
            List<Message> inbox = stub.showInboxs(authToken);

            // Print inbox
            for (Message msg : inbox) {
                if (msg.getIsRead() == false)
                    System.out.println(msg.getMessageID() + ". from: " + msg.getSender() + "*");
                if (msg.getIsRead() == true)
                    System.out.println(msg.getMessageID() + ". from: " + msg.getSender());
            }
        }
        else System.out.println("Invalid Auth Token");
    }

    public static void readMessage(int authToken, int message_id) throws RemoteException {
        if(stub.searchAuthToken(authToken)==true) {
            Message message = stub.readMessages(authToken, message_id);
            if (message != null) {
                System.out.println("(" + message.getSender() + ")" + message.getBody());
            }
            else System.out.println("Message ID does not exist");
        }
        else System.out.println("Invalid Auth Token");
    }

    public static void deleteMessage(int authToken, int message_id) throws RemoteException {
        if(stub.searchAuthToken(authToken)==true) {
            boolean flag = stub.deleteMessages(authToken, message_id);
            if(flag==true) System.out.println("Message does not exist");
            else System.out.println("OK");
        }
        else System.out.println("Invalid Auth Token");
    }

}
