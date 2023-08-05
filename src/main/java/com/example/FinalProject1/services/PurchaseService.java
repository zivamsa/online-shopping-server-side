package com.example.FinalProject1.services;

import com.example.FinalProject1.repository.PurchasesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {
    @Autowired
    PurchasesRepository repository;

}
