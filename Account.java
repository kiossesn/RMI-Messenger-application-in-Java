package Other;

import java.io.Serializable;
import java.util.*;

public class Account implements Serializable {
    private String username;
    private int authToken;
    protected List<Message> messageBox;

    public Account(String username, int authToken) {
        this.username = username;
        this.authToken = authToken;
        messageBox=new ArrayList<>();
        System.out.println("Account made");
    }
    public Account() {
        super();
    }
    public String getUsername(){
        return this.username;
    }
    public void setMessageBox(List<Message> messageBox) {
        this.messageBox = messageBox;
    }
    public int getAuthToken(){
        return this.authToken;
    }
    public void enterMessageBox(Message message){
        this.messageBox.add(message);
    }
    public List<Message> getMessageBox(){
        return this.messageBox;
    }
}
