package OnlineShopping.services;

import OnlineShopping.models.Product;
import OnlineShopping.models.User;
import OnlineShopping.models.UserProductInteraction;
import OnlineShopping.repository.UserProductInteractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProductInteractionService {
    @Autowired
    UserProductInteractionRepository repository;
    public void saveOrUpdate(UserProductInteraction interaction) {
        repository.save(interaction);
    }

    public UserProductInteraction findByUserProduct(User user, Product product) {
        return repository.findByUserAndProduct(user, product)
                .orElse(UserProductInteraction
                        .builder()
                        .user(user)
                        .product(product)
                        .build());
    }
}
