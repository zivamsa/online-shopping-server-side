package com.example.FinalProject1.repository;

import com.example.FinalProject1.models.Deals;
import com.example.FinalProject1.models.Purchases;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PurchasesRepository extends JpaRepository<Purchases, Deals> {
}
