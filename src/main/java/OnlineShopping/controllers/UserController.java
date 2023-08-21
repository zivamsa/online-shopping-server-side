package OnlineShopping.controllers;

import OnlineShopping.dto.PayloadUser;
import OnlineShopping.models.User;
import OnlineShopping.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/")
    public ResponseEntity<?> updateUser(@Valid @RequestBody PayloadUser user) {
        User updated = userService.updateUser(user);
        return ResponseEntity.ok(updated);
    }
}
