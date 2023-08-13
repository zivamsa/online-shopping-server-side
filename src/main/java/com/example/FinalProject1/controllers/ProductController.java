package com.example.FinalProject1.controllers;

import com.example.FinalProject1.auth.AuthenticationService;
import com.example.FinalProject1.exceptions.LackingPermissions;
import com.example.FinalProject1.models.Product;
import com.example.FinalProject1.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/product")
    public void addProduct(
            @RequestBody Product product,
            HttpServletRequest request
    ) throws LackingPermissions {
        if (!authenticationService.isUserAdmin(request)) {
            throw new LackingPermissions();
        }
        productService.saveOrUpdate(product);
    }

    @PutMapping("/product")
    public void updateProduct(
            @RequestBody Product product,
            HttpServletRequest request
    ) throws LackingPermissions {
        if (!authenticationService.isUserAdmin(request)) {
            throw new LackingPermissions();
        }
        productService.saveOrUpdate(product);
    }
}
