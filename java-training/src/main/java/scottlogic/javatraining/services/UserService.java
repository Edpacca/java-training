package scottlogic.javatraining.services;

import scottlogic.javatraining.interfaces.IUserService;
import scottlogic.javatraining.models.UserAccount;
import scottlogic.javatraining.models.UserAccountRequest;
import scottlogic.javatraining.repositories.UserRepository;

import java.util.List;
import java.util.UUID;

public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserAccount> getDbUserAccounts() {
        return userRepository.findAll();
    }

    public UserAccount getUserAccount(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserAccount postUserAccount(UserAccountRequest userAccountRequest) {
        UserAccount newUser = new UserAccount(userAccountRequest.name);
        userRepository.save(newUser);
        return(newUser);
    }
}
