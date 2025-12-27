package Model;

public class user {
    private int userId;
    private String username;
    private String passwordHash;
    private String email;
    private String role;
    private String status;

    public user() {
    }
    
    public user(int userId, String username, String passwordHash, String email, String role) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.role = role;
        this.status = "ACTIVE";
    }
    
    public user(int userId, String username, String passwordHash, String email, String role, String status) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getStatus() { return status; }
    
    public boolean isActive() {
    return "ACTIVE".equalsIgnoreCase(status);
}

    
}
