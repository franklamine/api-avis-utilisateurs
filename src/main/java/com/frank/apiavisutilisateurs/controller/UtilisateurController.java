package com.frank.apiavisutilisateurs.controller;

import com.frank.apiavisutilisateurs.configuration.JwtService;
import com.frank.apiavisutilisateurs.dto.AuthentificationDTO;
import com.frank.apiavisutilisateurs.entity.Utilisateur;
import com.frank.apiavisutilisateurs.service.UtilisateurService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

//@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(path = "utilisateurs")
public class UtilisateurController {

    private UtilisateurService utilisateurService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;


    @PostMapping(path = "inscription")
    public ResponseEntity<Utilisateur> incription(@RequestBody Utilisateur utilisateur) {
//        log.info("Inscription Utilisateur");
        return utilisateurService.inscription(utilisateur);
    }

    @PostMapping(path = "activation")
    public ResponseEntity<String> activation(@RequestBody Map<String, String> activation) {
        return utilisateurService.activation(activation);
    }

    @PostMapping(path = "connexion")
    public  Map<String, String> connexion(@RequestBody AuthentificationDTO authentificationDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authentificationDTO.username(), authentificationDTO.password()));
            Map<String, String> token=null;
            if (authentication.isAuthenticated()) {
                token = jwtService.generateToken(authentificationDTO.username());
            }
            return token;
        } catch (Exception e) {
            throw new RuntimeException("❌ Connexion échouée : " + e.getMessage());
        }
    }
}
