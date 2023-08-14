package com.example.FinalProject1;

import com.example.FinalProject1.models.Role;
import com.example.FinalProject1.models.User;
import com.example.FinalProject1.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest(classes = FinalProject1Application.class)
public class UserRepositoryTest {
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    public void testFindById() {
//        User user = getUser();
//        userRepository.save(user);
//        User result = userRepository.findById(user.getId()).get();
//        assertEquals(user.getUsername(), result.getUsername());
//    }
//
//    @Test
//    public void testFindAll() {
//        User user = getUser();
//        userRepository.save(user);
//        List<User> result = new ArrayList<>();
//        userRepository.findAll().forEach(u -> result.add(u));
//        assertEquals(result.size(), 1);
//    }
//    @Test
//    public void testSave() {
//        User user = getUser();
//        userRepository.save(user);
//        User found = userRepository.findById(user.getId()).get();
//        assertEquals(user.getUsername(), found.getUsername());
//    }
//
//    @Test
//    public void testFindByEmail() {
//        User user = getUser();
//        userRepository.save(user);
//        Optional<User> result = userRepository.findByEmail(user.getEmail());
//        assertNotNull(result);
//    }
//
//    private User getUser() {
//        User user = new User();
////        user.setId("");
//        user.setPassword("Mahesh");
//        user.setEmail("mahesh@test.com");
//        user.setAddress("Haim bar lev 25 Ness Ziona");
//        user.setRole(Role.ADMIN);
//        return user;
//    }
}
