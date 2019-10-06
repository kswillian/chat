package santos.williankaminski.chat.model;

import com.google.firebase.database.DatabaseReference;

import santos.williankaminski.chat.config.FirebaseConf;

/**
 * @author Willian Kaminski dos santos
 * @since 05-10-2019
 * @version 0.0.1
 */
public class Talk {

    private String idSender;
    private String idAddress;
    private String lastMessage;
    private String dataLastMessagem;
    private User user;

    public Talk() {
    }

    public String getIdSender() {
        return idSender;
    }

    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public String getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(String idAddress) {
        this.idAddress = idAddress;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getDataLastMessagem() {
        return dataLastMessagem;
    }

    public void setDataLastMessagem(String dataLastMessagem) {
        this.dataLastMessagem = dataLastMessagem;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void save(){

        DatabaseReference databaseReference = FirebaseConf.getFirenaseDatabase();
        DatabaseReference talkRef = databaseReference.child("talks");

        talkRef.child(this.getIdSender())
               .child(this.getIdSender())
               .setValue(this);
    }
}
