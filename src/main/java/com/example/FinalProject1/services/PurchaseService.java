package com.example.FinalProject1.services;

import com.example.FinalProject1.repo.PurchasesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseService {
    @Autowired
    PurchasesRepo repository;

}
