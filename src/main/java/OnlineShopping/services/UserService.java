package OnlineShopping.services;

import OnlineShopping.models.User;
import OnlineShopping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository repository;

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User saveOrUpdate(User user) {
        return repository.save(user);
    }

    public User updateUser(User updated) {
        User existing = repository.findById(updated.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        updated.setPassword(existing.getPassword());
        if (updated.getRole() == null) {
            updated.setRole(existing.getRole());
        }
        if (updated.getAddress() == null) {
            updated.setAddress(existing.getAddress());
        }
        if (updated.getRole() == null) {
            updated.setRole(existing.getRole());
        }
        if (updated.getFirstName() == null) {
            updated.setFirstName(existing.getFirstName());
        }
        if (updated.getLastName() == null) {
            updated.setLastName(existing.getLastName());
        }
        if (updated.getEmail() == null) {
            updated.setEmail(existing.getEmail());
        }

        User output = repository.save(updated);
        output.setPassword("");

        return output;
    }

}
