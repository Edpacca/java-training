package scottlogic.javatraining.interfaces;

import scottlogic.javatraining.models.UserAccount;
import scottlogic.javatraining.models.UserAccountRequest;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    List<UserAccount> getDbUserAccounts();
    UserAccount getUserAccount(UUID id);
    UserAccount postUserAccount(UserAccountRequest userAccountRequest);
}
