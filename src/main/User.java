package main;

// POJO Object

public class User {
    private String userID;
    private String password;
    private String firstName;
    private String secondName;
    private int role;


    public User(String userID, String password, String firstName, String secondName, int role) {
        this.userID = userID;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.role = role;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
