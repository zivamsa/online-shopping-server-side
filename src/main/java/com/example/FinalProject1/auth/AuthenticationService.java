package com.example.FinalProject1.auth;

import com.example.FinalProject1.config.JwtService;
import com.example.FinalProject1.exceptions.UserEmailAlreadyRegistered;
import com.example.FinalProject1.models.Role;
import com.example.FinalProject1.models.User;
import com.example.FinalProject1.repository.UserRepository;
import com.example.FinalProject1.token.Token;
import com.example.FinalProject1.token.TokenRepository;
import com.example.FinalProject1.token.TokenType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;


    public AuthenticationResponse register(RegisterRequest request) throws UserEmailAlreadyRegistered {
        Optional<User> userOptional = repository.findByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            throw new UserEmailAlreadyRegistered("User with this email already exists");
        }
        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .address(request.getAddress())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        User savedUser = repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .role(user.getRole())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .role(user.getRole())
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String refreshToken = this.extractHeaderRefreshToken(request).orElseThrow();
        User user = this.extractUser(refreshToken).orElseThrow();
        if (!jwtService.isTokenValid(refreshToken, user)) {
            return;
        }
        final String newAccessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, newAccessToken);
        var authResponse = AuthenticationResponse
                .builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
    }

    private Optional<String> extractHeaderRefreshToken(HttpServletRequest request) {
        final String prefix = "Bearer ";
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(prefix)) {
            return Optional.empty();
        }
        final String refreshToken = authHeader.substring(prefix.length());
        return Optional.ofNullable(refreshToken == "" ? null : refreshToken);
    }

    public Optional<User> extractUser(String refreshToken) {
        final String email = jwtService.extractUsername(refreshToken);
        if (email == null) {
            return Optional.empty();
        }
        return this.repository.findByEmail(email);
    }
}