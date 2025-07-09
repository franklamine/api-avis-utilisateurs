package com.frank.apisocialnetwork.entity;

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
    private Long id;

    @Lob
    @Column( columnDefinition = "TEXT")
    private String refreshToken;

    @Lob
    @Column( columnDefinition = "TEXT")
    private String AccessToken;

    @ManyToOne
    private Utilisateur utilisateur;

}
