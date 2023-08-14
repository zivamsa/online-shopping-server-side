package OnlineShopping.repository;

import OnlineShopping.models.Deals;
import OnlineShopping.models.Purchases;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PurchasesRepository extends JpaRepository<Purchases, Deals> {
}
