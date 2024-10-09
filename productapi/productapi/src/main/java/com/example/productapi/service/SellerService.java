package com.example.productapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.productapi.entity.Seller;
import com.example.productapi.repository.SellerRepository;


@Service
public class SellerService {
    @Autowired
    private SellerRepository repo;

    public void registerSeller(Seller seller) {
        repo.save(seller);
    }

    public boolean validateSeller(String email, String password) {
        Seller seller = repo.findByEmail(email);
        return seller != null && seller.getPassword().equals(password);
    }

    public Seller findbyemail(String email) {
        return repo.findByEmail(email);
    }

    public Seller getSellerById(Long sellerId) {
        return repo.findById(sellerId).orElse(null);
    }
}
