package scottlogic.javatraining.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scottlogic.javatraining.interfaces.IUserService;
import scottlogic.javatraining.models.UserAccount;
import scottlogic.javatraining.models.UserAccountRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UsersController {

    @Autowired
    private IUserService userService;

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

    @PostMapping(headers = "Accept=application/json")
    public ResponseEntity<UserAccount> postUserAccount(@RequestBody UserAccountRequest request) {
        UserAccount newUserAccount = userService.postUserAccount(request);
        return ResponseEntity.ok().body(newUserAccount);
    }
}
