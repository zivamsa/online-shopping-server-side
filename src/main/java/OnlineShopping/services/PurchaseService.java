package OnlineShopping.services;

import OnlineShopping.models.Purchase;
import OnlineShopping.repository.PurchasesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {
    @Autowired
    PurchasesRepository repository;
    public void saveOrUpdate(Purchase purchase) {
        repository.save(purchase);
    }

}
