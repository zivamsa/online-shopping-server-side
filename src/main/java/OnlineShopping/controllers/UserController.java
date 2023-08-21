package OnlineShopping.controllers;

import OnlineShopping.models.User;
import OnlineShopping.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        Optional<ObjectError> error = bindingResult.getAllErrors().stream().findFirst();
        if (error.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(error.get().getDefaultMessage());
        }
        User updated = userService.updateUser(user);
        return ResponseEntity.ok(updated);
    }
}
