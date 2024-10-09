package com.example.productapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.productapi.entity.Seller;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Seller findByEmail(String email);
}
