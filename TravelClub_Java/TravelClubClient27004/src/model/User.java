package model;

import java.io.Serializable;

public class User implements Serializable {
    public static final long serialVersionUID = 1L;

    private int userId;
    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;
    private EUserRole role = EUserRole.MEMBER;
    private boolean active = true;

    public User() {}

    public User(String email, String password, String fullName, String phoneNumber, EUserRole role) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.role = role != null ? role : EUserRole.MEMBER;
        this.active = true;
    }

    public int getUserId() {
        return userId; 
    }
    public void setUserId(int userId) { 
        this.userId = userId;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) { 
        this.email = email;
    }
    public String getPassword() {
        return password; 
    }
    public void setPassword(String password) { 
        this.password = password; 
    }
    public String getFullName() { 
        return fullName; 
    }
    public void setFullName(String fullName) { 
        this.fullName = fullName;
    }
    public String getPhoneNumber() { 
        return phoneNumber; 
    }
    public void setPhoneNumber(String phoneNumber) { 
        this.phoneNumber = phoneNumber;
    }
    public EUserRole getRole() { 
        return role;
    }
    public void setRole(EUserRole role) { 
        this.role = role;
    }
    public boolean isActive() { 
        return active; 
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() { 
        return fullName + " (" + email + ")"; 
    }
}
