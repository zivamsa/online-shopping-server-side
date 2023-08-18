package OnlineShopping.config;

import OnlineShopping.models.Token;
import OnlineShopping.models.User;
import OnlineShopping.repository.TokenRepository;
import OnlineShopping.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;
    private final AuthenticationService authenticationService;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        User user = authenticationService.getUserByRequest(request);
        if (user == null) return;
        Optional<Token> resToken = authenticationService.queryRefreshToken(user);
        if (resToken.isEmpty()) return;
        Token token = resToken.get();
        token.expiryDate = new Date();

        tokenRepository.save(token);
    }
}