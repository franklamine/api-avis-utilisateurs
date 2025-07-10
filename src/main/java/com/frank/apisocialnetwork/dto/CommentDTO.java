package com.frank.apisocialnetwork.dto;



public record CommentDTO(int PublicationId, String message, int like, String auteurComment, String photoAuteurComment) {
}

