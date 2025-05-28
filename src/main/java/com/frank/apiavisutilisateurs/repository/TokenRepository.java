package com.frank.apiavisutilisateurs.repository;

import com.frank.apiavisutilisateurs.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TokenRepository extends JpaRepository<Token, Integer> {

    Token findByRefreshToken(String bearer);
}
