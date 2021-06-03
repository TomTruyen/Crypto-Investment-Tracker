package be.tomtruyen.cryptotracker.domain.response;

import java.time.LocalDateTime;
import java.util.List;

public class CryptoResponse {
    private String path;
    private String message;
    private boolean success;
    private int status;
    private List<?> crypto;
    private LocalDateTime time;

    public CryptoResponse() {
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

    public List<?> getCrypto() {
        return crypto;
    }

    public void setCrypto(List<?> crypto) {
        this.crypto = crypto;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
