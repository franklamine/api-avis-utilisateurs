package com.frank.apiavisutilisateurs.configuration;

import com.frank.apiavisutilisateurs.entity.Token;
import com.frank.apiavisutilisateurs.entity.RefreshToken;
import com.frank.apiavisutilisateurs.entity.Utilisateur;
import com.frank.apiavisutilisateurs.repository.TokenRepository;
import com.frank.apiavisutilisateurs.service.TokenService;
import com.frank.apiavisutilisateurs.service.UtilisateurService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@AllArgsConstructor
@Service
public class JWTUtils {
    private UtilisateurService utilisateurService;
    private TokenRepository tokenRepository;

    private long calculExpiration(long duree){return duree * 60 * 1000; }

    public Token generateToken(String username) {
        Utilisateur utilisateur = (Utilisateur) utilisateurService.loadUserByUsername(username);
        return buildTokens(utilisateur);
    }

    private Token buildTokens(Utilisateur utilisateur) {
        final Map<String, Object> claims = new HashMap<>(Map.of("nom", utilisateur.getNom(), "roles",utilisateur.getAuthorities()));
        final String accessToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(utilisateur.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + calculExpiration(1)))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

        final String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(utilisateur.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + calculExpiration(30)))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

        RefreshToken refreshTokenObj = new RefreshToken();
        refreshTokenObj.setBearer(refreshToken);
        Token token = new Token();
        token.setBearer(accessToken);
        token.setRefreshToken(refreshTokenObj);
        token.setUtilisateur(utilisateur);
        tokenRepository.save(token);

        return token;
    }

    private Key getKey() {
        String ENCRYPTION_KEY = "MaCleSecreteTresLongueQuiFaitAuMoins32Caracteres!";
        final byte[] decoder = Decoders.BASE64.decode(ENCRYPTION_KEY);

        return Keys.hmacShaKeyFor(decoder);
    }

    public String extractUsername(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = getClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    private <T> T getClaim(String token, Function<Claims, T> function) {
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
