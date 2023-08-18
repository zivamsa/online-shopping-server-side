package OnlineShopping.controllers;

import OnlineShopping.models.Product;
import OnlineShopping.exceptions.LackingPermissions;
import OnlineShopping.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/product")
    public Product addProduct(@RequestBody Product product) {
        return productService.saveOrUpdate(product);
    }

    @PutMapping("/product")
    public Product updateProduct(@RequestBody Product product) {
        return productService.saveOrUpdate(product);
    }
}
