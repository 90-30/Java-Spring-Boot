package com.example.productapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.productapi.entity.Product;
import com.example.productapi.entity.Seller;
import com.example.productapi.service.ProductService;
import com.example.productapi.service.SellerService;

import java.util.List;

@RestController
@RequestMapping("/api/seller")
public class SellerController {

    @Autowired
    private SellerService service;

    @Autowired
    private ProductService pservice;

    @PostMapping("/register")
    public ResponseEntity<String> addSeller(@RequestBody Seller seller) {
        service.registerSeller(seller);
        return ResponseEntity.status(HttpStatus.CREATED).body("Seller registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginSeller(@RequestBody Seller seller) {
        boolean isValidSeller = service.validateSeller(seller.getEmail(), seller.getPassword());
        
        if (isValidSeller) {
            Seller loggedInSeller = service.findbyemail(seller.getEmail());
            return ResponseEntity.ok(loggedInSeller);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    @PostMapping("/add-product")
    public ResponseEntity<String> addProduct(@RequestBody Product product, @RequestParam Long sellerId) {
        Seller seller = service.getSellerById(sellerId);
        if (seller != null) {
            product.setSeller(seller);
            pservice.addProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body("Product added successfully!");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to add a product.");
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProductsBySellerId(@RequestParam("sellerId") Long sellerId) {
        List<Product> productList = pservice.getProductsBySellerId(sellerId);
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/search-products")
    public ResponseEntity<?> searchProducts(@RequestParam("query") String query, @RequestParam("sellerId") Long sellerId) {
        List<Product> products = pservice.searchProducts(query, sellerId);
        
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found.");
        }
        return ResponseEntity.ok(products);
    }

    @PutMapping("/edit-product")
    public ResponseEntity<String> editProduct(@RequestBody Product product, @RequestParam("sellerId") Long sellerId) {
        Seller seller = service.getSellerById(sellerId);
        
        if (seller != null) {
            product.setSeller(seller);
            pservice.updateProduct(product);
            return ResponseEntity.ok("Product updated successfully!");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to edit a product.");
    }

    @DeleteMapping("/delete-product")
    public ResponseEntity<String> deleteProduct(@RequestParam("id") Long productId) {
        pservice.deleteProduct(productId);
        return ResponseEntity.ok("Product deleted successfully.");
    }
}
