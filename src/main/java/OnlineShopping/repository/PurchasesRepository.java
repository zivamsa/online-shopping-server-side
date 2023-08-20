package OnlineShopping.repository;

import OnlineShopping.models.Deal;
import OnlineShopping.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PurchasesRepository extends JpaRepository<Purchase, Deal> {
}
