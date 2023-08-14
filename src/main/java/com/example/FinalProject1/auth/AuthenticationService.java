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
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;


    public AuthenticationResponse register(RegisterRequest request) throws UserEmailAlreadyRegistered {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

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
        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateAccessToken(savedUser);
        String refreshToken = jwtService.generateRefreshToken(savedUser);
        saveRefreshToken(savedUser, refreshToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .firstname(savedUser.getFirstname())
                .lastname(savedUser.getLastname())
                .role(savedUser.getRole())
                .build();
    }

    private void saveRefreshToken(User user, String refresh_token) {
        Date expiration = new Date(System.currentTimeMillis() + jwtService.refreshExpiration);
        Token token = Token
                .builder()
                .refreshToken(refresh_token)
                .expiryDate(expiration)
                .user(user)
                .build();
        tokenRepository.save(token);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var jwtToken = jwtService.generateAccessToken(user);
        var currentRefresh = tokenRepository
                .findByUser(user)
                .stream().filter(token -> !token.isExpired())
                .findFirst();
        String refreshToken;
        if (currentRefresh.isEmpty()) {
            refreshToken = jwtService.generateRefreshToken(user);
            saveRefreshToken(user, refreshToken);
        } else {
            refreshToken = currentRefresh.get().refreshToken;
        }
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .role(user.getRole())
                .build();
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
    }

    private String extractHeaderRefreshToken(HttpServletRequest request) {
        final String prefix = "Bearer ";
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(prefix)) {
            return "";
        }
        return authHeader.substring(prefix.length());
    }

    private User getUserByToken(String refreshToken) {
        final String email = jwtService.extractUsername(refreshToken);
        if (email == null) {
            return null;
        }
        return this.userRepository.findByEmail(email).orElse(null);
    }

    public AuthenticationResponse authenticateByToken(HttpServletRequest request) {
        final String token = extractHeaderRefreshToken(request);
        if (token == "") return null;
        User user = getUserByToken(token);
        if (user == null) return null;
        return AuthenticationResponse.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .role(user.getRole())
                .accessToken(token)
                .build();
    }

    public User getUserByRequest(HttpServletRequest request) {
        final String token = extractHeaderRefreshToken(request);
        if (token == "") return null;
        return getUserByToken(token);
    }

    public boolean isUserAdmin(HttpServletRequest request) {
        User user = getUserByRequest(request);
        if (user == null) return false;
        return user.isAdmin();
    }
}