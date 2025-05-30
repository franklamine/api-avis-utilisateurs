package com.frank.apisocialnetwork.repository;

import com.frank.apisocialnetwork.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TokenRepository extends JpaRepository<Token, Integer> {

    Token findByRefreshToken(String bearer);
}
