package com.frank.apisocialnetwork.dto;



public record CommentDTO(int PublicationId, String message, int like, UtilisateurDTO utilisateur) {
}

