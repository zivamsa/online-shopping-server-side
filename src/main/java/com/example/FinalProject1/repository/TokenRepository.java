package com.example.FinalProject1.repository;

import com.example.FinalProject1.models.Token;
import com.example.FinalProject1.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;


@RepositoryRestResource
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByRefreshToken(String token);
    List<Token> findByUser(User user);
}
