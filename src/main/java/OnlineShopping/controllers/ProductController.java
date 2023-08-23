package OnlineShopping.controllers;

import OnlineShopping.models.Product;
import OnlineShopping.exceptions.LackingPermissions;
import OnlineShopping.services.FileStorageService;
import OnlineShopping.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    FileStorageService fileStorageService;

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

    @PostMapping(path = "/product", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Product addProduct(
            @Valid @RequestPart Product product,
            @RequestPart(required = false) MultipartFile file
    ){
        //      INPUT FORMAT: form-data
        //      file: file
        //      product: Product in json format (as string)- use Content-Type: application/json on this field
        final Product savedProduct = productService.saveOrUpdate(product);

        // TODO: maybe- store images as an entity, connect to the product
        // TODO: check that the file is an image
        // TODO: upload file in update as well
        // TODO: change field name to image instead of file
        // TODO: return the image to the client(@Transient maybe)
        if (file != null) {
            fileStorageService.storeFile(file);
        }

        return savedProduct;
    }

    @PutMapping("/product")
    public Product updateProduct(@Valid @RequestBody Product product) {
        return productService.saveOrUpdate(product);
    }

    @PostMapping("/image")
    public boolean testImage(@RequestParam("file") MultipartFile file) {
        return fileStorageService.storeFile(file);
    }
}
