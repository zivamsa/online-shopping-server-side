package OnlineShopping.services;

import OnlineShopping.dto.CheckoutRequest;
import OnlineShopping.models.Deal;
import OnlineShopping.models.Product;
import OnlineShopping.models.Purchase;
import OnlineShopping.models.User;
import OnlineShopping.repository.DealsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DealService {
    @Autowired
    DealsRepository repository;
    @Autowired
    ProductService productService;

    public Deal getDealById(Long id) {
        return repository.findById(id).get();
    }

    public List<Deal> getAllDeals() {
        List<Deal> deals = repository.findAll();
        deals.forEach(this::prefixProductImage);
        return deals;
    }

    public void saveOrUpdate(Deal deal) {
        repository.save(deal);
    }

    public void createDeal(List<CheckoutRequest> purchases, User user) {
        var mappedPurchases = purchases.stream().map(purchase -> {
            final Product product = productService.getProductById(purchase.getProductId());
            if (product == null) {
                throw new Error(String.format("Product id %d does not exist", purchase.getProductId()));
            }
            return Purchase.builder()
                    .product(product)
                    .price(product.getPrice())
                    .amount(purchase.getCount())
                    .build();
        }).toList();

        var deal = Deal.builder()
                .user(user)
                .purchases(mappedPurchases)
                .commitDate(new Date())
                .build();
        deal.getPurchases().forEach((purchase) -> {
            purchase.setDeal(deal);
        });
        repository.save(deal);

        mappedPurchases.stream()
                .forEach((purchase) -> {
                    var product = purchase.getProduct();
                    product.setStock(product.getStock() - purchase.getAmount());
                    productService.saveOrUpdate(product);
                });
    }

    public List<Deal> getUserDeals(User user) {
        List<Deal> deals = repository.findByUser(user);
        deals.forEach(this::prefixProductImage);
        return deals;
    }

    private void prefixProductImage(Deal deal) {
        List<Purchase> purchases = deal.getPurchases();
        purchases.forEach(purchase -> {
            Product before = purchase.getProduct();
            Product after = productService.prefixProductPath(before);
            purchase.setProduct(after);
        });
    }
}
