package Server;
import Other.*;
import java.rmi.*;
import java.util.List;

public interface MessengerInt extends Remote {

    void sendMessages(Account sender, int authToken, Account receiver, String body) throws RemoteException;
    List<Message> showInboxs(int authToken) throws RemoteException;
    Message readMessages(int authToken, int messageID) throws RemoteException;
    int createAccounts(String username) throws RemoteException;
    boolean searchAuthToken(int authToken)throws RemoteException;
    Account findAccounts(String username)throws RemoteException;
    Account searchAccounts(int authToken)throws RemoteException;
    int generateTokens()throws RemoteException;
    String[] showAccountss()throws RemoteException;
    boolean deleteMessages(int authToken, int messageID)throws RemoteException;
}