package OnlineShopping.controllers;

import OnlineShopping.dto.*;
import OnlineShopping.exceptions.ApiError;
import OnlineShopping.exceptions.UserEmailAlreadyRegistered;
import OnlineShopping.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) throws UserEmailAlreadyRegistered {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @Valid @RequestBody LoginRequest request
    ) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException ex) {
            final String errorMessage;
            if (ex instanceof UsernameNotFoundException) {
                errorMessage = "Invalid Username";
            } else if (ex instanceof BadCredentialsException) {
                errorMessage = "Invalid Credentials";
            } else {
                errorMessage = "Authentication Error";
            }
            ApiError error = new ApiError(HttpStatus.BAD_REQUEST, errorMessage);
            return new ResponseEntity<>(error, error.getStatus());
        }

        return ResponseEntity.ok(service.login(request));
    }
    @GetMapping("/authenticate")
    public ResponseEntity<?> authenticate(HttpServletRequest request) {
        var out = service.authenticateByToken(request);
        if (out == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(out);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(
            @Valid @RequestBody TokenRefreshRequest request
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
