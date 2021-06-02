package be.tomtruyen.cryptotracker.rest.resources;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class EmailResource {
    @NotEmpty(message = "Email can't be empty")
    @Email(message = "Email is invalid")
    private String email;

    public EmailResource() {}

    public EmailResource(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
