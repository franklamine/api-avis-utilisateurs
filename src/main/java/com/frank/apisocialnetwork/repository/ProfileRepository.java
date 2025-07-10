package com.frank.apisocialnetwork.repository;

import com.frank.apisocialnetwork.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
}
