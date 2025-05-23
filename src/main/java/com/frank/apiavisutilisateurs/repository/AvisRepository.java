package com.frank.apiavisutilisateurs.repository;

import com.frank.apiavisutilisateurs.entity.Avis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvisRepository extends JpaRepository<Avis, Integer> {
}
