package model;

public class Student {
    private String userId;
    private int fine;
    private boolean status;
    private static int FINE_LIMIT;

    static {
        FINE_LIMIT = 30;
    }

    public Student(String userId, int fine, boolean status) {
        this.userId = userId;
        this.fine = fine;
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine += fine;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        if (getFine() > FINE_LIMIT) {
            this.status = true;
        } else {
            this.status = status;
        }
    }
}
