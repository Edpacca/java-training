package scottlogic.javatraining.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AuthenticationRequest {

    @NotEmpty
    @NotNull
    private String username;
    @NotEmpty
    @NotNull
    private String password;

    public AuthenticationRequest() {}

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
