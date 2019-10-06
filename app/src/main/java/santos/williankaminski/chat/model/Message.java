package santos.williankaminski.chat.model;

import java.util.Date;

/**
 * @author Willian Kaminski dos santos
 * @since 06-10-2019
 * @version 0.0.1
 */
public class Message {

    private String idUser;
    private String message;
    private String photo;
    private String date;
    private String status;

    public Message() {
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
