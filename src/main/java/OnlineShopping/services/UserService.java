package OnlineShopping.services;

import OnlineShopping.dto.PayloadUser;
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

    public User updateUser(PayloadUser updated) {
        User existing = repository.findById(updated.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (updated.getRole() != null) {
            existing.setRole(updated.getRole());
        }
        if (updated.getAddress() != null) {
            existing.setAddress(updated.getAddress());
        }
        if (updated.getEmail() != null) {
            existing.setEmail(updated.getEmail());
        }
        if (updated.getFirstName() != null) {
            existing.setFirstName(updated.getFirstName());
        }
        if (updated.getLastName() != null) {
            existing.setLastName(updated.getLastName());
        }

        User output = repository.save(existing);

        return output;
    }

}
