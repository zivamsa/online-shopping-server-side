package com.example.FinalProject1.services;

import com.example.FinalProject1.exceptions.TokenExpired;
import com.example.FinalProject1.models.Token;
import com.example.FinalProject1.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    @Autowired
    TokenRepository repository;

    public Token verifyExpiration(Token token) throws TokenExpired {
        if (token.isExpired()) {
            throw new TokenExpired();
        }

        return token;
    }

}
