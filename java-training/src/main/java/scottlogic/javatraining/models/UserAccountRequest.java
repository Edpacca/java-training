package scottlogic.javatraining.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserAccountRequest {
    @NotEmpty
    @NotNull
    public String username;
    @NotNull
    @NotEmpty
    public String password;
}
