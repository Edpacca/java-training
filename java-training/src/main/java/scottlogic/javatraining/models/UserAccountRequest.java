package scottlogic.javatraining.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserAccountRequest {
    @NotEmpty
    @NotNull
    @NotEmpty
    public String name;
}
