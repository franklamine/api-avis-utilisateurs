package com.frank.apisocialnetwork.service;

import com.frank.apisocialnetwork.entity.Publication;
import com.frank.apisocialnetwork.entity.Utilisateur;
import com.frank.apisocialnetwork.repository.PublicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PublicationService {
    private final PublicationRepository avisRepository;

    public ResponseEntity<Publication> creerAvis(Publication avis) {
        Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        avis.setUtilisateur(utilisateur);
        return new ResponseEntity<>(avisRepository.save(avis), HttpStatus.CREATED);
    }
}
