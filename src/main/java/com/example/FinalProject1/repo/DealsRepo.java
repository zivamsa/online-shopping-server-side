package com.example.FinalProject1.repo;

import com.example.FinalProject1.models.Deals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface DealsRepo extends JpaRepository<Deals, Long> {
}
