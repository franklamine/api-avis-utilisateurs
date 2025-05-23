package com.frank.apiavisutilisateurs.service;

import com.frank.apiavisutilisateurs.dto.AuthentificationDTO;
import com.frank.apiavisutilisateurs.entity.Role;
import com.frank.apiavisutilisateurs.entity.Utilisateur;
import com.frank.apiavisutilisateurs.entity.Validation;
import com.frank.apiavisutilisateurs.enumerateur.TypeRole;
import com.frank.apiavisutilisateurs.repository.UtilisteurRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UtilisateurService implements UserDetailsService {

    private UtilisteurRepository utilisateurRepository;
    private PasswordEncoder passwordEncoder;
    private ValidationService validationService;


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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findByEmail(username);
        if (utilisateur.isEmpty()) {
            throw new UsernameNotFoundException( "Utilisateur " +username + " n'a pas été trouvé" );
        }
        return utilisateur.get();
    }

}
