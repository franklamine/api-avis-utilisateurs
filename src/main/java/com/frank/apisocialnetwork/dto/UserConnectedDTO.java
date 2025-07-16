package com.frank.apisocialnetwork.dto;

public record UserConnectedDTO(
        int id,
        String nom,
        String prenom,
        String photoProfileUserConnected,
        String photoCouvertureUserConnected
) {
}
