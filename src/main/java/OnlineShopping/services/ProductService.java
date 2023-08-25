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

    public Product saveProduct(Product product, MultipartFile image) {
        product.setId(null);
        // this ensures that the file must be uploaded- could remove this to allow upload by url
        product.setImagePath(null);
        Product saved = saveOrUpdate(product);

        String imagePath = null;
        if (image != null) {
            imagePath = fileStorageService.uploadProduct(image, saved.getId());
        } else {
            imagePath = fileStorageService.productImagePath("placeholder.png");
        }
        product.setImagePath(imagePath);

        return saveOrUpdate(product);
    }

    public Product updateProduct(Product product, MultipartFile image) {
        String imagePath;
        if (image == null) {
            // this ensures that the file must be uploaded- could remove this to allow upload by url
            Product existing = repository.findById(product.getId()).orElseThrow(() -> new Error("Product does not exist!"));
            imagePath = existing.getImagePath();
        } else {
            imagePath = fileStorageService.uploadProduct(image, product.getId());
        }

        product.setImagePath(imagePath);
        return saveOrUpdate(product);
    }

    public Product saveOrUpdate(Product product) {
        return repository.save(product);
    }
}
