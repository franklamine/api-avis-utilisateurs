package com.frank.apiavisutilisateurs.controller;
import com.frank.apiavisutilisateurs.service.TokenService;
import com.frank.apiavisutilisateurs.dto.AuthentificationDTO;
import com.frank.apiavisutilisateurs.entity.Utilisateur;
import com.frank.apiavisutilisateurs.service.UtilisateurService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    private TokenService tokenService;


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
    public   Map<String, String> connexion(@RequestBody AuthentificationDTO authentificationDTO) {
      return utilisateurService.connexion(authentificationDTO);
    }

    @PostMapping("refresh-token")
    public Map<String, String> refreshToken(@RequestBody Map<String, String> refreshToken) {
        return tokenService.refreshToken(refreshToken);
    }

    @PostMapping("deconnexion")
    public String deconnexion(@RequestBody Map<String, String> refreshToken) {
        return utilisateurService.deconnexion(refreshToken);
    }
}
