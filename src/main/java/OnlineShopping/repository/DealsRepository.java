package OnlineShopping.repository;

import OnlineShopping.models.Deals;
import OnlineShopping.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface DealsRepository extends JpaRepository<Deals, Long> {
    List<Deals> findByUser(User user);
}
