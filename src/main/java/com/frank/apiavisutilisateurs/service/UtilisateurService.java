package com.frank.apiavisutilisateurs.service;

import com.frank.apiavisutilisateurs.dto.AuthentificationDTO;
import com.frank.apiavisutilisateurs.entity.Role;
import com.frank.apiavisutilisateurs.entity.Token;
import com.frank.apiavisutilisateurs.entity.Utilisateur;
import com.frank.apiavisutilisateurs.entity.Validation;
import com.frank.apiavisutilisateurs.enumerateur.TypeRole;
import com.frank.apiavisutilisateurs.repository.TokenRepository;
import com.frank.apiavisutilisateurs.repository.UtilisteurRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Transactional
@AllArgsConstructor
@Service
public class UtilisateurService {

    private UtilisteurRepository utilisateurRepository;
    private PasswordEncoder passwordEncoder;
    private ValidationService validationService;
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private TokenRepository tokenRepository;
    private CustomUserDetailsService customUserDetailsService;


    public ResponseEntity<Utilisateur> inscription(Utilisateur utilisateur) {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByEmail(utilisateur.getEmail());
        if (utilisateurOptional.isPresent()) {
            throw new RuntimeException("Utilisateur existe deja");
        }
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        utilisateur.setRole(new Role());
        utilisateur.getRole().setTypeRole(TypeRole.UTILISATEUR);
        utilisateur = utilisateurRepository.save(utilisateur);

        validationService.enregistrerValidation(utilisateur);
        return new ResponseEntity<>(utilisateur, HttpStatus.CREATED);
    }

    public ResponseEntity<String> activation(Map<String, String> activation) {
        Validation validation = validationService.getValidationByCode(activation.get("code"));
        if (Instant.now().isAfter(validation.getExpiration())) {
            throw new RuntimeException("Votre code d'activation a expiré");
        }
        Optional<Utilisateur> utilisateurAActiver = utilisateurRepository.findById(validation.getUtilisateur().getId());
        if (utilisateurAActiver.isEmpty()) {
            throw new RuntimeException("Utilisateur n'existe pas");
        }
        utilisateurAActiver.get().setActif(true);
        utilisateurRepository.save(utilisateurAActiver.get());
        return new ResponseEntity<>("cher " + utilisateurAActiver.get().getNom() + " votre compte a été activé .", HttpStatus.OK);
    }

    public Map<String, String> connexion(@RequestBody AuthentificationDTO authentificationDTO) {
        Map<String, String> tokens = new HashMap<>();
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authentificationDTO.username(), authentificationDTO.password()));
            if (authentication.isAuthenticated()) {
                String accessToken = tokenService.generateToken(authentificationDTO.username(), true);
                String refreshToken = tokenService.generateToken(authentificationDTO.username(), false);
                tokens.put("access-token", accessToken);
                tokens.put("refresh-token", refreshToken);
                Token tokenObj = new Token();
                tokenObj.setAccessToken(accessToken);
                tokenObj.setRefreshToken(refreshToken);
                tokenObj.setUtilisateur((Utilisateur) customUserDetailsService.loadUserByUsername(authentificationDTO.username()));
                tokenRepository.save(tokenObj);
            }
            return tokens;
        } catch (Exception e) {
            throw new RuntimeException("Connexion échouée : " + e.getMessage());
        }
    }

    public String deconnexion(Map<String, String> refreshToken) {
        Token token = tokenRepository.findByRefreshToken( refreshToken.get("token"));
        if (token == null) {
            throw new RuntimeException("Token non trouve");
        }
        tokenRepository.delete(token);
        return "deconnection reussie";
    }
}
