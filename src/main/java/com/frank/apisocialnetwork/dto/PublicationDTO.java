package com.frank.apisocialnetwork.dto;


import java.time.LocalDateTime;
import java.util.List;

public record PublicationDTO(
        int id,
        String message,
        String photo, // en base64
        String video, // en base64
        UtilisateurDTO utilisateur,
        List<CommentDTO> comments,
        int likes,
        LocalDateTime date
) {}

