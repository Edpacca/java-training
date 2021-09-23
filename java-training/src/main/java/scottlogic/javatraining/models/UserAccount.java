package scottlogic.javatraining.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.*;

@NoArgsConstructor
public class UserAccount {
    @Id
    @Getter
    private UUID id;
    @Getter
    @Setter
    private String name;

    public UserAccount(String name) {
        this.name = name;
        this.id = UUID.randomUUID();
    }
}
