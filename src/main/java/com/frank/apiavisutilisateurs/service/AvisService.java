package com.frank.apiavisutilisateurs.service;

import com.frank.apiavisutilisateurs.entity.Avis;
import com.frank.apiavisutilisateurs.entity.Utilisateur;
import com.frank.apiavisutilisateurs.repository.AvisRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AvisService {
    private final AvisRepository avisRepository;

    public ResponseEntity<Avis> creerAvis(Avis avis) {
        Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        avis.setUtilisateur(utilisateur);
        return new ResponseEntity<>(avisRepository.save(avis), HttpStatus.CREATED);
    }
}
