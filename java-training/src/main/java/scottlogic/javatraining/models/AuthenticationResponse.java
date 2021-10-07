package scottlogic.javatraining.models;

import lombok.Getter;

import java.util.UUID;

@Getter
public class AuthenticationResponse {

    private final String jwt;
    private final UUID id;
    private final String username;

    public AuthenticationResponse(String jwt, UserAccount userAccount) {
        this.jwt = jwt;
        this.id = userAccount.getId();
        this.username = userAccount.getUsername();
    }
}
