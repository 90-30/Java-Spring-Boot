package com.example.productapi.repository;

import com.example.productapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findBySeller_SellerId(Long sellerId);

    List<Product> findBySeller_SellerIdAndNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
        Long sellerId, String name, String description);
}
