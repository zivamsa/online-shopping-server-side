package com.example.FinalProject1.auth;

import com.example.FinalProject1.exceptions.UserEmailAlreadyRegistered;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
    @GetMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(HttpServletRequest request,
                                             HttpServletResponse response) {
        var out = service.authenticateByToken(request);
        if (out == null) {
            return (ResponseEntity<AuthenticationResponse>) ResponseEntity.notFound();
        }
        return ResponseEntity.ok(out);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }
}
