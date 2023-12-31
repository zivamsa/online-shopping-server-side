package com.example.FinalProject1.controllers;

import com.example.FinalProject1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/auth/{id}")
    public String getUserAuth(@PathVariable("user_id") String id) {
        return userService.getUserAuthById(id);
    }
}
