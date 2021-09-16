package scottlogic.javatraining.models;

import java.util.*;

public class UserAccount {
    private final UUID id = UUID.randomUUID();
    private String name;

    public UserAccount(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
