package OnlineShopping.services;

import OnlineShopping.dto.AuthenticationResponse;
import OnlineShopping.dto.LoginRequest;
import OnlineShopping.dto.RegisterRequest;
import OnlineShopping.exceptions.TokenDoesntExist;
import OnlineShopping.exceptions.TokenExpired;
import OnlineShopping.exceptions.UserEmailAlreadyRegistered;
import OnlineShopping.repository.TokenRepository;
import OnlineShopping.repository.UserRepository;
import OnlineShopping.models.Role;
import OnlineShopping.models.User;
import OnlineShopping.models.Token;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
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
        String refreshToken = createRefreshToken(savedUser);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .firstname(savedUser.getFirstname())
                .lastname(savedUser.getLastname())
                .role(savedUser.getRole())
                .build();
    }

    public AuthenticationResponse login(LoginRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String jwtToken = jwtService.generateAccessToken(user);
        var currentRefresh = queryRefreshToken(user);
        String refreshToken;
        if (currentRefresh.isEmpty()) {
            refreshToken = createRefreshToken(user);
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

    public String refreshToken(String refreshToken) throws TokenDoesntExist, TokenExpired {
        Optional<Token> dbTokenOptional = tokenRepository.findByRefreshToken(refreshToken);
        if (dbTokenOptional.isEmpty()) {
            throw new TokenDoesntExist();
        }

        Token dbToken = tokenService.verifyExpiration(dbTokenOptional.get());
        User user = dbToken.getUser();
        return jwtService.generateAccessToken(user);
    }

    private String createRefreshToken(User user) {
        String refreshToken = jwtService.generateRefreshToken(user);
        Date expiration = new Date(System.currentTimeMillis() + jwtService.refreshExpiration);
        Token token = Token
                .builder()
                .refreshToken(refreshToken)
                .expiryDate(expiration)
                .user(user)
                .build();
        tokenRepository.save(token);

        return refreshToken;
    }

    public String extractHeaderAccessToken(HttpServletRequest request) {
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

    public Optional<Token> queryRefreshToken(User user) {
        return tokenRepository
                .findByUser(user)
                .stream().filter((currDBToken)->!currDBToken.isExpired())
                .findFirst();
    }

    public AuthenticationResponse authenticateByToken(HttpServletRequest request) {
        final String token = extractHeaderAccessToken(request);
        if (token == "") return null;
        User user = getUserByToken(token);
        if (user == null) return null;
        String refreshToken = queryRefreshToken(user).orElseThrow().refreshToken;
        return AuthenticationResponse.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .role(user.getRole())
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    public User getUserByRequest(HttpServletRequest request) {
        final String token = extractHeaderAccessToken(request);
        if (token == "") return null;
        return getUserByToken(token);
    }
}