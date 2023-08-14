package OnlineShopping.services;

import OnlineShopping.exceptions.TokenExpired;
import OnlineShopping.repository.TokenRepository;
import OnlineShopping.models.Token;
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
