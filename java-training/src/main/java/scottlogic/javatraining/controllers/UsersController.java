package scottlogic.javatraining.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import scottlogic.javatraining.authentication.AuthTokenUtils;
import scottlogic.javatraining.interfaces.IUserService;
import scottlogic.javatraining.models.UserAccount;
import scottlogic.javatraining.models.UserAccountRequest;
import scottlogic.javatraining.models.AuthenticationRequest;
import scottlogic.javatraining.models.AuthenticationResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("users")
@CrossOrigin
public class UsersController {

    @Autowired
    private IUserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthTokenUtils authTokenUtils;

    @PostMapping(path="/signup", headers = "Accept=application/json")
    public ResponseEntity<UserAccount> postUserAccount(@Valid @RequestBody UserAccountRequest request) {
        UserAccount newUser = userService.postUserAccount(request);
        if (newUser == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().body(newUser);
    }

    @PostMapping (path = "/login")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody AuthenticationRequest authenticationRequest)
        throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));
        } catch (BadCredentialsException exception) {
            throw new Exception("Incorrect username or password", exception);
        }

        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = authTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
