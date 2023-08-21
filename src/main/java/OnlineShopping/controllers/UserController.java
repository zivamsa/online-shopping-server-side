package OnlineShopping.controllers;

import OnlineShopping.models.User;
import OnlineShopping.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }
}
