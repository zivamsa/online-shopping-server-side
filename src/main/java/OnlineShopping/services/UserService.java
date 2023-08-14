package OnlineShopping.services;

import OnlineShopping.repository.UserRepository;
import OnlineShopping.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository repository;

    public User getUserById(Integer id) {
        return repository.findById(id).get();
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
