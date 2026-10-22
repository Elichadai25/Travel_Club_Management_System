package model;

import java.io.Serializable;
import java.util.Date;

public class Notification implements Serializable {
    public static final long serialVersionUID = 1L;

    private int notificationId;
    private String message;
    private Date sentAt;
    private String type;
    private boolean read = false;
    private User user;

    public Notification() {}

    public Notification(int notificationId, String message, Date sentAt, String type, User user) {
        this.notificationId = notificationId;
        this.message = message;
        this.sentAt = sentAt;
        this.type = type;
        this.user = user;
    }

    public Notification(String msg, String type, User u) {
        this.message = msg;
        this.type = type;
        this.user = u;
        this.sentAt = new java.util.Date();
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}