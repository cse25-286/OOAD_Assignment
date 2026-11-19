
package bank.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {

    private static final int MAX_FAILED_ATTEMPTS = 3;

    private final String username;
    private final String passwordHash;
    private final String role;

    private int failedAttempts = 0;
    private boolean locked = false;

    // Constructor
    public User(String username, String password, String role) {
        this.username = username;
        this.passwordHash = hash(password);
        this.role = role;
    }

    // Check username's password
    public boolean authenticate(String password) {

        if (locked) {
            return false;
        }

        if (passwordHash.equals(hash(password))) {
            failedAttempts = 0;
            return true;
        }

        failedAttempts++;

        if (failedAttempts >= MAX_FAILED_ATTEMPTS) {
            locked = true;
        }

        return false;
    }

    // Unlock a user account
    public void unlock() {
        locked = false;
        failedAttempts = 0;
    }

    // Convert password into a hash
    private static String hash(String text) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] bytes = md.digest(
                text.getBytes(StandardCharsets.UTF_8)
            );

            StringBuilder sb = new StringBuilder();

            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    // Getter methods
    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getRole() {
        return role;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public boolean isLocked() {
        return locked;
    }
}
