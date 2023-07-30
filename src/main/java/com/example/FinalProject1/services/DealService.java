package com.example.FinalProject1.services;

import com.example.FinalProject1.models.Deals;
import com.example.FinalProject1.repo.DealsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DealService {
    @Autowired
    DealsRepo repository;

    public Deals getDealById(Long id) {
        return repository.findById(id).get();
    }

    public List<Deals> getAllDeals(){
        List<Deals> deals = new ArrayList<Deals>();
        repository.findAll().forEach(deal -> deals.add(deal));
        return deals;
    }
    public void saveOrUpdate(Deals deal) {
        repository.save(deal);
    }
}
