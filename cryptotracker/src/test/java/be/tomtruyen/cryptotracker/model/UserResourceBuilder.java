package be.tomtruyen.cryptotracker.model;

import be.tomtruyen.cryptotracker.rest.resources.UserResource;

public class UserResourceBuilder {
    private static final String EMAIL = "test@user.resource";
    private static final String PASSWORD = "Password1234/";

    private String email = EMAIL;
    private String password = PASSWORD;

    private UserResourceBuilder() {}

    public static UserResourceBuilder anUserResource() {
        return new UserResourceBuilder();
    }

    public UserResourceBuilder withEmail(String email) {
        this.email = email;

        return this;
    }

    public UserResourceBuilder withPassword(String password) {
        this.password = password;

        return this;
    }

    public UserResource build() {
        UserResource userResource = new UserResource();
        userResource.setEmail(email);
        userResource.setPassword(password);
        return userResource;
    }




}
