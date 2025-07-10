package com.frank.apisocialnetwork.service;

import com.frank.apisocialnetwork.dto.CommentDTO;
import com.frank.apisocialnetwork.dto.PublicationDTO;
import com.frank.apisocialnetwork.dto.UtilisateurDTO;
import com.frank.apisocialnetwork.entity.Profile;
import com.frank.apisocialnetwork.entity.Publication;
import com.frank.apisocialnetwork.entity.Utilisateur;
import com.frank.apisocialnetwork.exception.ApiSocialNetworkException;
import com.frank.apisocialnetwork.repository.PublicationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
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
                if (!photo.getContentType().startsWith("image/")) {
                    throw new ApiSocialNetworkException("Fichier image non valide", HttpStatus.BAD_REQUEST);
                }
                publication.setPhoto(photo.getBytes());
            }
            if (video != null && !video.isEmpty()) {
                if (!video.getContentType().startsWith("video/")) {
                    throw new ApiSocialNetworkException("Fichier vidéo non valide", HttpStatus.BAD_REQUEST);
                }
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

                      String auteurPublication = publication.getUtilisateur().getNom() + " " + publication.getUtilisateur().getPrenom();

                      Profile profile = publication.getUtilisateur().getProfile();
                      String photoAuteurPublicationBase64 = profile.getPhotoProfile() != null ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(profile.getPhotoProfile()) : null;

                      List<CommentDTO> commentDTOs = publication.getComments().stream()
//                              .sorted((c1, c2) -> c2.getId().compareTo(c1.getId())) // tri du plus récent au plus ancien
                              .map(comment -> {
                                  Utilisateur utilisateur = comment.getUtilisateur();
                                  Profile profile1 = utilisateur.getProfile();
                                  String photoAuteurComentBase64 = profile1.getPhotoProfile() != null ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(profile1.getPhotoProfile()) : null;

                                  return new CommentDTO(
                                      publication.getId(),
                                      comment.getMessage(),
                                      comment.getLikes(),
                                        utilisateur.getNom() + " " + utilisateur.getPrenom(),
                                          photoAuteurComentBase64
                              );
                              }).collect(Collectors.toList());

                      return new PublicationDTO(
                              publication.getId(),
                              publication.getMessage(),
                              photoBase64,
                              videoBase64,
                              auteurPublication,
                              photoAuteurPublicationBase64,
                              commentDTOs,
                              publication.getLikes(),
                              publication.getCreatedAt()
                      );
                  }).collect(Collectors.toList());

        return new ResponseEntity<>(publicationDTOS, HttpStatus.OK);
    }

}
