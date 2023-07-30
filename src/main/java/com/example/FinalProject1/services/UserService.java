package com.example.FinalProject1.services;

import com.example.FinalProject1.models.User;
import com.example.FinalProject1.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepo repository;

    public User getUserById(String id) {
        return repository.findById(id).get();
    }

    public String getUserAuthById(String id){
        return repository.findById(id).get().getAuth();
    }
    public List<User> getAllUsers(){
        List<User> users = new ArrayList<User>();
        repository.findAll().forEach(user -> users.add(user));
        return users;
    }
    public void saveOrUpdate(User user) {
        repository.save(user);
    }
}
