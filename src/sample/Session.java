package sample;

public enum Session {
    USER;
    private String userId;
    private Roles role = Roles.OWNER; // Hard-coded for testing purposes

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
