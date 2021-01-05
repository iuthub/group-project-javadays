package main.java;

public class Result {
    private String uid;
    private String name;
    private int count;

    public Result(String uid, String name){
        this.uid = uid;
        this.name = name;
        this.count = 3;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
