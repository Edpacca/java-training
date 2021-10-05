package scottlogic.javatraining.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import scottlogic.javatraining.interfaces.IUserService;
import scottlogic.javatraining.models.UserAccount;
import scottlogic.javatraining.models.UserAccountRequest;
import scottlogic.javatraining.repositories.UserRepository;

public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public UserAccount postUserAccount(UserAccountRequest userAccountRequest) {
        if (!validateUserName(userAccountRequest.username)) {
            UserAccount newUser = new UserAccount(userAccountRequest);
            saveUserAccount(newUser);
            return(newUser);
        } else return  null;
    }

    private void saveUserAccount(UserAccount user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    private Boolean validateUserName(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
