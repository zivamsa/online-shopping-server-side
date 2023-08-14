package OnlineShopping.repository;

import OnlineShopping.models.Deals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface DealsRepository extends JpaRepository<Deals, Long> {
}
