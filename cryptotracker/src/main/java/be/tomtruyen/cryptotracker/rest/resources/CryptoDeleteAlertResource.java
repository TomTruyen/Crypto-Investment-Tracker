package be.tomtruyen.cryptotracker.rest.resources;

import javax.validation.constraints.Min;

public class CryptoDeleteAlertResource {
    @Min(value = 1, message = "Id invalid")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
