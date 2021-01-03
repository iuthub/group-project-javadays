package main;

public class User {
    private String userID;
    private String login;
    private String password;
    private String firstName;
    private String secondName;
    private int role;


    public User(String userID, String login, String password, String firstName, String secondName, int role) {
        this.userID = userID;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.role = role;
    }

    public String getFirstName() { return firstName; }

    public String getSecondName() { return secondName; }


}
