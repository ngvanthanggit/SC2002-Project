package utility;

/**
 * Represents a password reset request submitted by a user.
 * <p>
 * This class encapsulates the user's ID and name for identifying and processing
 * password reset requests.
 */
public class PasswordResetRequest {
    private String userId;
    private String userName;

    /**
     * Constructs a new {@code PasswordResetRequest} with the specified user ID and name.
     * 
     * @param userId   The ID of the user requesting the password reset.
     * @param userName The name of the user requesting the password reset.
     */
    public PasswordResetRequest(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    /**
     * Retrieves the ID of the user requesting the password reset.
     * 
     * @return The user ID as a {@code String}.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Retrieves the name of the user requesting the password reset.
     * 
     * @return The user name as a {@code String}.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns a string representation of the password reset request.
     * <p>
     * The format is: {@code UserID: <userId>, UserName: <userName>}.
     * 
     * @return A formatted string representing the password reset request.
     */
    @Override
    public String toString() {
        return String.format("UserID: %s, UserName: %s", userId, userName);
    }
}
