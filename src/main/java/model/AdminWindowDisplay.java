package model;

public class AdminWindowDisplay {
    private String userId;
    private String name;
    private int count;

    public AdminWindowDisplay(String userId, String name){
        this.userId = userId;
        this.name = name;
        this.count = 3;
    }

    public AdminWindowDisplay(String userId, String name, int count){
        this.userId = userId;
        this.name = name;
        this.count = count;
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
