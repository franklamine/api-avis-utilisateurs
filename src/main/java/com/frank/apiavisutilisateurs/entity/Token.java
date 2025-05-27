package com.frank.apiavisutilisateurs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String bearer;
    private boolean disable = false;

    @OneToOne(cascade = CascadeType.ALL)
    private RefreshToken refreshToken;

    @ManyToOne(cascade = CascadeType.ALL)
    private Utilisateur utilisateur;

}
