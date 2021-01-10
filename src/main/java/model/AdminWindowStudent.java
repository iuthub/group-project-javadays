package model;

public class AdminWindowStudent {
    private String userId;
    private String name;
    private int count;

    public AdminWindowStudent(String userId, String name){
        this.userId = userId;
        this.name = name;
        this.count = 3;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
