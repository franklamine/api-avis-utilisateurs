package com.frank.apisocialnetwork.repository;

import com.frank.apisocialnetwork.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> findByRefreshToken(String bearer);
}
