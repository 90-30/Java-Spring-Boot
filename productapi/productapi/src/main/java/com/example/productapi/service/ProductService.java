package com.example.productapi.service;

import com.example.productapi.entity.Product;
import com.example.productapi.entity.Seller;
import com.example.productapi.repository.ProductRepository;
import com.example.productapi.repository.SellerRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository repo;

    @Autowired
    private SellerRepository srepo;

    public void addProduct(Product product) {
        Seller seller = product.getSeller();
        
        if (seller != null && seller.getSellerId() != null) {
            Seller managedSeller = srepo.findById(seller.getSellerId())
                .orElseThrow(() -> new RuntimeException("Seller not found"));
            product.setSeller(managedSeller);
        }
        product.setProductAddedAt(LocalDate.now());
        repo.save(product);  
    }

    public List<Product> getProductsBySellerId(Long sellerId) {
        return repo.findBySeller_SellerId(sellerId);  
    }

    public List<Product> searchProducts(String query, Long sellerId) {
        return repo.findBySeller_SellerIdAndNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(sellerId, query, query);
    }

    public Product getProductById(Long productId) {
        return repo.findById(productId).orElse(null);  
    }

    public void updateProduct(Product product) {
        repo.save(product);  
    }

    public void deleteProduct(Long productId) {
        repo.deleteById(productId);
    }
}
