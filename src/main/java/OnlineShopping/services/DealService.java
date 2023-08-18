package OnlineShopping.services;

import OnlineShopping.dto.CheckoutRequest;
import OnlineShopping.models.Deals;
import OnlineShopping.models.Product;
import OnlineShopping.models.Purchases;
import OnlineShopping.models.User;
import OnlineShopping.repository.DealsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DealService {
    @Autowired
    DealsRepository repository;
    @Autowired
    ProductService productService;

    public Deals getDealById(Long id) {
        return repository.findById(id).get();
    }

    public List<Deals> getAllDeals() {
        List<Deals> deals = new ArrayList<Deals>();
        repository.findAll().forEach(deal -> deals.add(deal));
        return deals;
    }

    public void saveOrUpdate(Deals deal) {
        repository.save(deal);
    }

    public void createDeal(List<CheckoutRequest> purchases, User user) {
        var mappedPurchases = purchases.stream().map(purchase -> {
            final Product product = productService.getProductById(purchase.getProductId());
            return Purchases.builder()
                    .product(product)
                    .price(product.getPrice())
                    .amount(purchase.getCount())
                    .build();
        }).toList();

        var deal = Deals.builder().user(user).purchases(mappedPurchases).build();
        deal.getPurchases().forEach((purchase) -> {
            purchase.setDeal(deal);
        });
        repository.save(deal);
    }
}
