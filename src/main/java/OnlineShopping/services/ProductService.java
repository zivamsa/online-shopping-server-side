package OnlineShopping.services;

import OnlineShopping.models.Product;
import OnlineShopping.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository repository;

    @Autowired
    FileStorageService fileStorageService;


    public Product getProductById(Long id) {
        return repository.findById(id).get();
    }

    public List<Product> getAllProducts(){
        List<Product> products = new ArrayList<Product>();
        repository.findAll().forEach(product -> products.add(product));
        return products;
    }
    public Product saveOrUpdate(Product product, MultipartFile image) {
        // TODO: maybe- store images as an entity, connect to the product
        // TODO: return the image to the client(@Transient maybe)

        Product savedProduct = repository.save(product);
        if (image != null) {
            fileStorageService.uploadProduct(image, savedProduct.getId());
        }

        return savedProduct;
    }

    public Product saveOrUpdate(Product product) {
        return saveOrUpdate(product, null);
    }
}
