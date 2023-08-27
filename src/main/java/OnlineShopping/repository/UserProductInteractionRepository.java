package OnlineShopping.repository;

import OnlineShopping.models.Product;
import OnlineShopping.models.User;
import OnlineShopping.models.UserProductInteraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserProductInteractionRepository extends JpaRepository<UserProductInteraction, Long> {
    Optional<UserProductInteraction> findByUserAndProduct(User user, Product product);
}
