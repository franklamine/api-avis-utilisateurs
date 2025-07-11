package com.frank.apisocialnetwork.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "publications")
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String message;
    private int likes;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] photo;

    @Lob //il sagit des données binaires
    @Basic(fetch = FetchType.LAZY)//tu demandes à JPA de ne charger ce champ que lorsqu’on y accède explicitement.
    private byte[] video;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @ManyToOne
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "publication")
    private List<Comment> comments;



}
