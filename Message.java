package Other;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Message implements Serializable {
    private static int counter = 0;
    private boolean isRead;
    private String sender;
    private String receiver;
    private String body;
    private int messageID;


    public Message(boolean isRead, String sender, String receiver, String body) {
        this.isRead = isRead;
        this.sender = sender;
        this.receiver = receiver;
        this.body = body;
        counter++;
        messageID=counter;
        System.out.println("Other.Message made");
    }

    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean read) {
        isRead = read;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getBody() {
        return body;
    }

    public int getMessageID() {
        return messageID;
    }
}
