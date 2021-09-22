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
        throw new UnsupportedOperationException();
    }

    @Override
    public UserAccount getUserAccount(UUID id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UserAccount postUserAccount(UserAccountRequest userAccountRequest) {
        throw new UnsupportedOperationException();
    }
}
