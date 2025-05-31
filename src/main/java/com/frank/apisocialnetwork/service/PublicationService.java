package com.frank.apisocialnetwork.service;

import com.frank.apisocialnetwork.dto.PublicationDTO;
import com.frank.apisocialnetwork.dto.UtilisateurDTO;
import com.frank.apisocialnetwork.entity.Publication;
import com.frank.apisocialnetwork.entity.Utilisateur;
import com.frank.apisocialnetwork.exception.ApiSocialNetworkException;
import com.frank.apisocialnetwork.repository.PublicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PublicationService {
    private final PublicationRepository publicationRepository;

    public ResponseEntity<String> creerPublication(String message, MultipartFile photo, MultipartFile video) {
        try {
            Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Publication publication = new Publication();
            publication.setUtilisateur(utilisateur);
            publication.setMessage(message);

            if (photo != null && !photo.isEmpty()) {
                publication.setPhoto(photo.getBytes());
            }
            if (video != null && !video.isEmpty()) {
                publication.setVideo(video.getBytes());
            }

            publicationRepository.save(publication);
        } catch (Exception e) {
            throw new ApiSocialNetworkException("une ereur est survenue pendant la publication", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("publication effectuée", HttpStatus.CREATED);
    }

    public ResponseEntity<List<PublicationDTO>> getAllPublications() {
          List<PublicationDTO> publicationDTOS = publicationRepository.findAllByOrderByCreatedAtDesc().stream()
                  .map(publication -> {
                      String photoBase64 = publication.getPhoto() != null
                              ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(publication.getPhoto())
                              : null;

                      String videoBase64 = publication.getVideo() != null
                              ? "data:video/mp4;base64," + Base64.getEncoder().encodeToString(publication.getVideo())
                              : null;

                      Utilisateur utilisateur = publication.getUtilisateur();
                      UtilisateurDTO utilisateurDTO = new UtilisateurDTO(utilisateur.getPrenom());

                      return new PublicationDTO(
                              publication.getMessage(),
                              photoBase64,
                              videoBase64,
                              utilisateurDTO
                      );
                  }).collect(Collectors.toList());

        return new ResponseEntity<>(publicationDTOS, HttpStatus.OK);
    }

}
