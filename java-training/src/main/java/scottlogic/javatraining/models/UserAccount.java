package scottlogic.javatraining.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

@NoArgsConstructor
public class UserAccount implements UserDetails {
    @Id
    @Getter
    @NotNull
    private UUID id;
    @Getter
    @Setter
    @NotNull
    @NotEmpty
    private String username;
    @Getter
    @Setter
    private String password;

    public UserAccount(UserAccountRequest user) {
        this.username = user.username;
        this.password = user.password;
        this.id = UUID.randomUUID();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
