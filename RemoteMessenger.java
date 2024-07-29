package Server;
import Client.*;
import Other.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RemoteMessenger extends UnicastRemoteObject implements MessengerInt{
    private ArrayList<Account> accounts;
    private static int counter = 0;


    protected RemoteMessenger() throws RemoteException {
        super();
        accounts=new ArrayList<>();
    }
    @Override
    public int createAccounts(String username) throws RemoteException {
        System.out.println("**********CREATE ACCOUNT**********");
        // Check username existence
        if(findAccounts(username)!=null){
            System.out.println("Sorry, the user already exists");
            return -1;
        }
        // Check username validation
        if (username.matches("[a-zA-Z_]+")) {
            System.out.println("The username is valid.");
            // Generate new authToken
            int authToken=generateTokens();
            // Make a new account
            Account account=new Account(username,authToken);
            // Enter new account into accounts list
            accounts.add(account);
            return authToken;
        }
        else {
            System.out.println("Invalid Username");
            return -2;
        }
    }
    @Override
    public int generateTokens() throws RemoteException {
        counter=counter+1;
        System.out.println("Token generated: "+counter);
        return counter;
    }

    @Override
    public Account findAccounts(String username) throws RemoteException {
        for (Account account : accounts) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }
        return null;
    }
    @Override
    public Account searchAccounts(int authToken)throws RemoteException{
        for (Account account : accounts) {
            if (account.getAuthToken()==authToken) {
                return account;
            }
        }
        return null;
    }
    @Override
    public boolean searchAuthToken(int authToken)throws RemoteException{
        for (Account account : accounts) {
            if (account.getAuthToken()==authToken) {
                return true;
            }
        }
        return false;
    }
    @Override
    public synchronized void sendMessages(Account sender, int authToken, Account receiver, String body) throws RemoteException {
        System.out.println("**********SEND MESSAGE**********");
        // Make new message
        Message message=new Message(false, sender.getUsername(), receiver.getUsername(), body);

        // Find receiver's account and remove it from accounts list
        for(Account a:accounts){
            if(a.getAuthToken()==receiver.getAuthToken()){
                accounts.remove(a);
                break;
            }
        }
        // Make a new Other.Account object for receiver
        Account receiver2= new Account(receiver.getUsername(),receiver.getAuthToken());
        // Set new account's messageBox with old and new messages
        receiver2.setMessageBox(receiver.getMessageBox());
        receiver2.enterMessageBox(message);
        // Add new account into accounts list
        accounts.add(receiver2);

        System.out.println("Message has been sent from "+sender.getUsername()+" to "+receiver2.getUsername());

        // Print receiver's messageBox in server
        System.out.println(receiver2.getUsername()+"'s inbox: ");
        for(Message msg: receiver2.getMessageBox()){
            if(msg.getIsRead()==false) System.out.println(msg.getMessageID()+". from: "+msg.getSender()+"*");
            if(msg.getIsRead()==true) System.out.println(msg.getMessageID()+". from: "+msg.getSender());
        }
    }
    @Override
    public synchronized List<Message> showInboxs(int authToken) throws RemoteException{
        System.out.println("**********SHOW INBOX**********");
        // Find account by auth token
        Account account = searchAccounts(authToken);
        if(account!=null)System.out.println("Receiver found ("+account.getUsername()+")");
        else {
            System.out.println("Receiver not found");
            return null;
        }
        // Print inbox to the server
        for(Message msg: account.getMessageBox()){
            if(msg.getIsRead()==false) System.out.println(msg.getMessageID()+". from: "+msg.getSender()+"*");
            if(msg.getIsRead()==true) System.out.println(msg.getMessageID()+". from: "+msg.getSender());
        }
        // Return account's messageBox
        return account.getMessageBox();
    }
    @Override
    public Message readMessages(int authToken, int messageID) throws RemoteException{
        System.out.println("**********READ MESSAGE**********");
        // Find account by auth token
        Account account = searchAccounts(authToken);
        // Find message with messageId from account's messageBox
        for(Message message: account.getMessageBox()){
            if(message.getMessageID()==messageID){
                System.out.println("("+message.getSender()+")"+message.getBody());
                message.setIsRead(true);
                // Return specific message
                return message;
            }
        }
        System.out.println("Message ID does not exist");
        return null;
    }
    @Override
    public String [] showAccountss() throws RemoteException {
        String[] usernames=new String[100];
        int i=0;
        for(Account account:accounts) {
            usernames[i]=account.getUsername();
            i++;
            System.out.println(i+". "+account.getUsername());
        }
        return usernames;
    }
    @Override
    public boolean deleteMessages(int authToken, int messageID) throws RemoteException {
        System.out.println("**********DELETE MESSAGE**********");
        boolean flag=true;
        // Find account by auth token
        Account account = searchAccounts(authToken);
        if(account!=null)System.out.println("Account found");
        else System.out.println("Account not found");
        // Remove account from accounts list
        for(Account a:accounts){
            if(a.getAuthToken()==account.getAuthToken()){
                accounts.remove(a);
                break;
            }
        }
        // Make new account
        Account newAccount=new Account(account.getUsername(),account.getAuthToken());
        // Set new account's messageBox and remove selected message
        newAccount.setMessageBox(account.getMessageBox());
        for(Message m: newAccount.getMessageBox()){
            if(m.getMessageID()==messageID){
                flag=false;
                newAccount.getMessageBox().remove(m);
                break;
            }
        }
        // Add new account into accounts list
        accounts.add(newAccount);
        return flag;
    }
}


