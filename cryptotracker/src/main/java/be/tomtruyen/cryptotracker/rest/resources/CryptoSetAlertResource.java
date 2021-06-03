package be.tomtruyen.cryptotracker.rest.resources;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class CryptoSetAlertResource {
    @Min(value = 1, message = "Id invalid")
    private Long id;
    @DecimalMin(value = "0.0", inclusive = false, message = "Alert price must be greater than zero")
    private double alert;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAlert() {
        return alert;
    }

    public void setAlert(double alert) {
        this.alert = alert;
    }
}
