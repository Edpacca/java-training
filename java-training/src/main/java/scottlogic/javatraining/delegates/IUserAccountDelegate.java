package scottlogic.javatraining.delegates;

import scottlogic.javatraining.models.UserAccount;
import scottlogic.javatraining.models.UserAccountRequest;

import java.util.List;
import java.util.UUID;

public interface IUserAccountDelegate {
    List<UserAccount> getDbUserAccounts();
    UserAccount getUserAccount(UUID id);
    UserAccount postUserAccount(UserAccountRequest userAccountRequest);
}
