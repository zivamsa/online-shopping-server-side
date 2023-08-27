package OnlineShopping.services;

import OnlineShopping.models.Product;
import OnlineShopping.models.User;
import OnlineShopping.models.UserProductInteraction;
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

    @Autowired
    UserProductInteractionService productInteractionService;


    public Product getProductById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Product> getAllProducts(){
        List<Product> products = repository.findAll();
        products.forEach(this::prefixProductPath);
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
        Product res = repository.save(product);
        if (product.getImagePath() != null) {
            res = prefixProductPath(res);
        }
        return res;
    }

    public Product prefixProductPath(Product product) {
        if (product.getImagePath() == null) return product;
        String host = fileStorageService.getHost();
        if (product.getImagePath().startsWith(host)) {
            return product;
        }
        String fullPath = String.format("%s/%s", fileStorageService.getHost() ,product.getImagePath());
        product.setImagePath(fullPath);
        return product;
    }

    public boolean toggleWishlist(Long productId, User user) {
        Product product = this.getProductById(productId);
        if (product == null) {
            throw new Error(String.format("Product id %d does not exist", productId));
        }
        UserProductInteraction interaction = productInteractionService.findByUserProduct(user, product);
        interaction.setWishlist(!interaction.isWishlist());

        productInteractionService.saveOrUpdate(interaction);
        return interaction.isWishlist();
    }
}
