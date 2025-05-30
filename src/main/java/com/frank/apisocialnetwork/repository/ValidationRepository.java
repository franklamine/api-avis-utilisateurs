package com.frank.apisocialnetwork.repository;

import com.frank.apisocialnetwork.entity.Validation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValidationRepository extends JpaRepository<Validation, Integer> {

    Optional<Validation> findByCode(String code);
}
