package main;

/**
 * <h1>Session</h1>
 * <p>
 *     Singleton class used for storing the current user session. The user's role is used to determine the features that
 *     the user is allowed to access.
 * </p>
 *
 * @author Arif Hasan
 * @version 1.0
 * @since 03/19/21
 */
public enum Session {
    USER;
    private String userId;
    private Roles role;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
