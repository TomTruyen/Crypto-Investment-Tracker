package be.tomtruyen.cryptotracker.domain.response;

import java.time.LocalDateTime;

public class UserResponse {
    private String path;
    private String message;
    private String token;
    private boolean success;
    private int status;
    private LocalDateTime time;

    public UserResponse() {
        this.message = "";
        this.success = false;
        this.time = LocalDateTime.now();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
