package com.example.FinalProject1.token;

import com.example.FinalProject1.exceptions.TokenExpired;
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
