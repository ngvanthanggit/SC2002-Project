package utility;

public class PasswordResetRequest {
    private String userId;
    private String userName;

    public PasswordResetRequest(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return String.format("UserID: %s, UserName: %s", userId, userName);
    }
}
