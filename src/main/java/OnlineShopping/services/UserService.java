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

    public List<User> getAllUsers(){
        return repository.findAll();
    }
    public void saveOrUpdate(User user) {
        repository.save(user);
    }
}
