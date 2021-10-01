package scottlogic.javatraining.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import scottlogic.javatraining.authentication.JWTokenUtil;
import scottlogic.javatraining.interfaces.IUserService;
import scottlogic.javatraining.models.UserAccount;
import scottlogic.javatraining.models.UserAccountRequest;
import scottlogic.javatraining.models.AuthenticationRequest;
import scottlogic.javatraining.models.AuthenticationResponse;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
@CrossOrigin
public class UsersController {

    @Autowired
    private IUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTokenUtil jwTokenUtil;

    @GetMapping
    public ResponseEntity<List<UserAccount>> getUserAccounts() {
        List<UserAccount> users = userService.getDbUserAccounts();
        return users == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok().body(users);
    }

    @GetMapping (path="/{id}")
    public ResponseEntity<UserAccount> getUserAccount(@PathVariable final UUID id) {
        UserAccount requestedUserAccount = userService.getUserAccount(id);

        return requestedUserAccount == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok().body(requestedUserAccount);
    }

    @PostMapping(path="/signup", headers = "Accept=application/json")
    public ResponseEntity<UserAccount> postUserAccount(@Valid @RequestBody UserAccountRequest request) {

        if (userService.checkUsername(request.username)) {
            // TODO return useful message
            return ResponseEntity.badRequest().build();
        } else {
            UserAccount newUser = userService.postUserAccount(request);
            return ResponseEntity.ok().body(newUser);
        }
    }

    @PostMapping (path = "/login")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody AuthenticationRequest authenticationRequest)
        throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserAccount userAccount =
                (UserAccount) userService.getUserAccountByName(authenticationRequest.getUsername());

        final String token = jwTokenUtil.generateToken(userAccount);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }





}
