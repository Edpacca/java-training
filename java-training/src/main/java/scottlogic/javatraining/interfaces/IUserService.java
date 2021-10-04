package scottlogic.javatraining.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;
import scottlogic.javatraining.models.UserAccount;
import scottlogic.javatraining.models.UserAccountRequest;

public interface IUserService extends UserDetailsService {
    UserAccount postUserAccount(UserAccountRequest userAccountRequest);
}