package scottlogic.javatraining.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import scottlogic.javatraining.interfaces.IUserService;
import scottlogic.javatraining.models.UserAccount;
import scottlogic.javatraining.models.UserAccountRequest;
import scottlogic.javatraining.repositories.UserRepository;

import java.util.List;
import java.util.UUID;

public class UserService implements IUserService, UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public List<UserAccount> getDbUserAccounts() {
        return userRepository.findAll();
    }

    public UserAccount getUserAccount(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserAccount postUserAccount(UserAccountRequest userAccountRequest) {
        UserAccount newUser = new UserAccount(userAccountRequest);
        saveUserAccount(newUser);
        return(newUser);
    }

    public void saveUserAccount(UserAccount user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public Boolean checkUsername(String username) {
        return userRepository.findByUsername(username) != null;
    }

    public UserAccount getUserAccountByName(String username) throws UsernameNotFoundException {
        UserAccount user = userRepository.findByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("username not found");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserAccount user = userRepository.findByUsername(username);
//        if (user != null) {
//            List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
//            return buildUserForAuthentication(user, authorities);
//        } else {
//            throw new UsernameNotFoundException("username not found");
//        }
//    }
//
//    private List<GrantedAuthority> getUserAuthority(List<Role> userRoles) {
//        List<GrantedAuthority> roles = new ArrayList<>();
//        userRoles.forEach(role -> {
//            roles.add(new SimpleGrantedAuthority(role.getRole()));
//        });
//        return roles;
//    }
//
//    private UserDetails buildUserForAuthentication(UserAccount user, List<GrantedAuthority> authorities) {
//        return new User(user.getUsername(), user.getPassword(), authorities);
//    }



}
