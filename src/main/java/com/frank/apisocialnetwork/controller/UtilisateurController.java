package com.frank.apisocialnetwork.controller;

import com.frank.apisocialnetwork.service.TokenService;
import com.frank.apisocialnetwork.dto.AuthentificationDTO;
import com.frank.apisocialnetwork.entity.Utilisateur;
import com.frank.apisocialnetwork.service.UtilisateurService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@AllArgsConstructor
@RestController
@RequestMapping(path = "utilisateurs")
public class UtilisateurController {

    private UtilisateurService utilisateurService;
    private TokenService tokenService;


    @PostMapping(path = "inscription")
    public ResponseEntity<String> incription(@RequestBody Utilisateur utilisateur) {
//        log.info("Inscription Utilisateur");
        return utilisateurService.inscription(utilisateur);
    }

    @PostMapping(path = "activation")
    public ResponseEntity<String> activation(@RequestBody Map<String, String> activation) {
        return utilisateurService.activation(activation);
    }

    @PostMapping(path = "connexion")
    public ResponseEntity<Map<String, String>>  connexion(@RequestBody AuthentificationDTO authentificationDTO, HttpServletResponse response) {
        return utilisateurService.connexion(authentificationDTO, response);
    }

    @PostMapping("refresh-token")
    public Map<String, String> refreshToken(@RequestBody Map<String, String> refreshToken) {
        return tokenService.refreshToken(refreshToken);
    }

    @PostMapping("deconnexion")
    public ResponseEntity<String> deconnexion(@RequestBody Map<String, String> refreshToken) {
        return utilisateurService.deconnexion(refreshToken);
    }
}
