package OnlineShopping.controllers;

import OnlineShopping.models.Product;
import OnlineShopping.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(path = "/product", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Product addProduct(
            @RequestPart(name = "product") @Valid Product product,
            @RequestPart(name = "image", required = false) MultipartFile image
    ) {
        //      INPUT FORMAT: form-data
        //      image: image
        //      product: Product in json format (as string)- use Content-Type: application/json on this field
        return productService.saveOrUpdate(product, image);
    }

    @PutMapping("/product")
    public Product updateProduct(
            @RequestPart(name = "product") @Valid Product product,
            @RequestPart(name = "image", required = false) MultipartFile image
    ) {
        return productService.saveOrUpdate(product, image);
    }
}
