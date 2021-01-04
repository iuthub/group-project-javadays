package main.java;

public class User {
    private String userId;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;

    public User(String userId, String password, String firstName, String lastName, Role role) {
        setUserId(userId);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
        setRole(role);
    }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public Role getRole() { return role; }

    public void setRole(Role role) { this.role = role; }
}
