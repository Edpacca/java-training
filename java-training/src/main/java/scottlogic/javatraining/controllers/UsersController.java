package scottlogic.javatraining.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scottlogic.javatraining.delegates.IUserAccountDelegate;
import scottlogic.javatraining.models.UserAccount;
import scottlogic.javatraining.models.UserAccountRequest;

import java.util.List;
import java.util.UUID;

@RestController
public class UsersController {

    @Autowired
    private IUserAccountDelegate userDelegate;

    @GetMapping(path ="/users")
    public ResponseEntity<List<UserAccount>> getUserAccounts() {
        List<UserAccount> orders = userDelegate.getDbUserAccounts();
        return orders == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok().body(orders);
    }

    @GetMapping (path="/user/{id}")
    public ResponseEntity<UserAccount> getUserAccount(@PathVariable final UUID id) {
        UserAccount requestedUserAccount = userDelegate.getUserAccount(id);

        return requestedUserAccount == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok().body(requestedUserAccount);
    }

    @PostMapping(path="/user", headers = "Accept=application/json")
    public ResponseEntity<UserAccount> postUserAccount(@RequestBody UserAccountRequest request) {
        UserAccount newUserAccount = userDelegate.postUserAccount(request);
        return ResponseEntity.ok().body(newUserAccount);
    }
}
