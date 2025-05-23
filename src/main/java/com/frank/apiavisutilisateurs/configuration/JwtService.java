package com.frank.apiavisutilisateurs.configuration;

import com.frank.apiavisutilisateurs.service.UtilisateurService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class JwtService {
    private final String ENCRYPTION_KEY = "MaCleSecreteTresLongueQuiFaitAuMoins32Caracteres!";
    private UtilisateurService utilisateurService;

    public Map<String, String> generateToken(String email) {
        UserDetails utilisateur = utilisateurService.loadUserByUsername(email);
        return generateJwt(utilisateur);
    }

    private Map<String, String> generateJwt(UserDetails utilisateur) {

        final Map<String, Object> claims = new HashMap<>(Map.of("email", utilisateur.getUsername(), "password", utilisateur.getPassword()));
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 30 * 60 * 1000;
        final String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(utilisateur.getUsername())
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

        return Map.of("bearer", bearer);
    }

    private Key getKey() {
        final byte[] decoder = Decoders.BASE64.decode(ENCRYPTION_KEY);

        return Keys.hmacShaKeyFor(decoder);
    }
}
