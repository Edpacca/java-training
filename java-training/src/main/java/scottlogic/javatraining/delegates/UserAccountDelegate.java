package scottlogic.javatraining.delegates;

import org.springframework.stereotype.Component;
import scottlogic.javatraining.models.UserAccount;
import scottlogic.javatraining.models.UserAccountRequest;

import java.util.List;
import java.util.UUID;

@Component("userDelegate")
public class UserAccountDelegate implements IUserAccountDelegate {
    @Override
    public List<UserAccount> getDbUserAccounts() {
        return null;
    }

    @Override
    public UserAccount getUserAccount(UUID id) {
        return null;
    }

    @Override
    public UserAccount postUserAccount(UserAccountRequest userAccountRequest) {
        return null;
    }
}
