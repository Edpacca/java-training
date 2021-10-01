package scottlogic.javatraining.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;
import scottlogic.javatraining.models.UserAccount;
import scottlogic.javatraining.models.UserAccountRequest;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    List<UserAccount> getDbUserAccounts();
    UserAccount getUserAccount(UUID id);
    UserAccount postUserAccount(UserAccountRequest userAccountRequest);
    UserAccount getUserAccountByName(String name);
    Boolean checkUsername(String username);
}
