package com.frank.apisocialnetwork.repository;

import com.frank.apisocialnetwork.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisteurRepository extends JpaRepository<Utilisateur, Integer> {

    Optional<Utilisateur> findByEmail(String email);
}
