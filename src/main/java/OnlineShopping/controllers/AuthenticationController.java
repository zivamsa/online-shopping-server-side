package OnlineShopping.controllers;

import OnlineShopping.dto.*;
import OnlineShopping.exceptions.UserEmailAlreadyRegistered;
import OnlineShopping.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) throws UserEmailAlreadyRegistered {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(service.login(request));
    }
    @GetMapping("/authenticate")
    public ResponseEntity<?> authenticate(HttpServletRequest request,
                                             HttpServletResponse response) {
        var out = service.authenticateByToken(request);
        if (out == null) {
            return (ResponseEntity<?>) ResponseEntity.notFound();
        }
        return ResponseEntity.ok(out);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(
            @RequestBody TokenRefreshRequest request
    ) {
        final String refreshToken = request.getRefreshToken();
        try {
            final String token = service.refreshToken(refreshToken);
            return ResponseEntity.ok(
                    TokenRefreshResponse
                            .builder()
                            .refreshToken(refreshToken)
                            .accessToken(token)
                            .build()
            );
        } catch (Exception e) {
            return (ResponseEntity<?>) ResponseEntity.notFound();
        }
    }
}
