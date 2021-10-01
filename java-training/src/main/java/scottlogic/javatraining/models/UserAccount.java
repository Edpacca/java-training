package scottlogic.javatraining.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

@NoArgsConstructor
public class UserAccount {
    @Id
    @Getter
    @NotNull
    private UUID id;
    @Getter
    @Setter
    @NotNull
    @NotEmpty
    private String name;

    public UserAccount(String name) {
        this.name = name;
        this.id = UUID.randomUUID();
    }
}
