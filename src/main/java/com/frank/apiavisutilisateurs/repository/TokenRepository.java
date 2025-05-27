package com.frank.apiavisutilisateurs.repository;

import com.frank.apiavisutilisateurs.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("FROM Token t WHERE t.refreshToken.bearer = :bearer")
    Token findByRefreshTokenBearer(String bearer);
}
