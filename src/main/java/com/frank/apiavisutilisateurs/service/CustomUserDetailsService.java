package com.frank.apiavisutilisateurs.service;

import com.frank.apiavisutilisateurs.entity.Utilisateur;
import com.frank.apiavisutilisateurs.repository.UtilisteurRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UtilisteurRepository utilisateurRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findByEmail(username);
        if (utilisateur.isEmpty()) {
            throw new UsernameNotFoundException("Utilisateur " + username + " n'a pas été trouvé");
        }
        return utilisateur.get();
    }
}
