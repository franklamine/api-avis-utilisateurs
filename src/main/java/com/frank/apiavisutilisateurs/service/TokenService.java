package com.frank.apiavisutilisateurs.service;

import com.frank.apiavisutilisateurs.configuration.JWTUtils;
import com.frank.apiavisutilisateurs.entity.Token;
import com.frank.apiavisutilisateurs.entity.Utilisateur;
import com.frank.apiavisutilisateurs.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TokenService {
    private TokenRepository tokenRepository;
    private JWTUtils jwtUtils;


    public Token refreshToken(Map<String, String> refresh) {
        String valeurRefresh = refresh.get("token");
        Token token = tokenRepository.findByRefreshTokenBearer(valeurRefresh);
        if (token == null) {
            throw new RuntimeException("Token non trouve");
        }
        if (jwtUtils.isTokenExpired(valeurRefresh)) {
            throw new RuntimeException("JWT expired");
        }

        return jwtUtils.generateToken(token.getUtilisateur().getUsername());
    }
}
