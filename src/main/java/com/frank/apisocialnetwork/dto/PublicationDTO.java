package com.frank.apisocialnetwork.dto;

public record PublicationDTO(
        String message,
        String photo, // en base64
        String video, // en base64
        UtilisateurDTO utilisateur
) {}

