package com.blog.blognest.controller;

import com.blog.blognest.DTO.AuthResponse;
import com.blog.blognest.DTO.RefreshToken;
import com.blog.blognest.DTO.RefreshTokenRequest;
import com.blog.blognest.entity.LoginRequest;
import com.blog.blognest.entity.Users;
import com.blog.blognest.repository.RefreshTokenRepository;
import com.blog.blognest.repository.UsersRepo;
import com.blog.blognest.util.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepo;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        String accessToken = jwtUtil.generateAccessToken(userDetails.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

        RefreshToken tokenEntity = new RefreshToken();
        tokenEntity.setToken(refreshToken);
        tokenEntity.setEmail(userDetails.getUsername());
        tokenEntity.setExpiryDate(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7)); // 7 days
        refreshTokenRepo.save(tokenEntity);

        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
    }


    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        Optional<RefreshToken> tokenOpt = refreshTokenRepo.findByToken(refreshToken);
        if (tokenOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }

        RefreshToken token = tokenOpt.get();

        if (token.getExpiryDate().before(new Date())) {
            refreshTokenRepo.delete(token);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token expired");
        }

        String newAccessToken = jwtUtil.generateAccessToken(token.getEmail());
        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }




    @PostMapping("/logout")
    @Transactional
    public ResponseEntity<String> logout(@RequestBody RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        refreshTokenRepo.deleteByToken(refreshToken);

        return ResponseEntity.ok("Logged out successfully");
    }

}
