package OnlineShopping.repository;

import OnlineShopping.models.Deal;
import OnlineShopping.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface DealsRepository extends JpaRepository<Deal, Long> {
    List<Deal> findByUser(User user);
}
