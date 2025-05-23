package com.frank.apiavisutilisateurs.service;

import com.frank.apiavisutilisateurs.entity.Utilisateur;
import com.frank.apiavisutilisateurs.entity.Validation;
import com.frank.apiavisutilisateurs.repository.ValidationRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

@AllArgsConstructor
@Service
public class ValidationService {

    private ValidationRepository validationRepository;
    private NotificationService notificationService;

    public void enregistrerValidation(Utilisateur utilisateur) {
        Validation validation = new Validation();
        validation.setUtilisateur(utilisateur);
        Instant creation = Instant.now();
        validation.setCreation(creation);

        Instant expiration = creation.plus(10, ChronoUnit.MINUTES);
        validation.setExpiration(expiration);

        Random random = new Random();
        int ramdomInt = random.nextInt(999999);
        String code = String.format("%06d", ramdomInt);
        validation.setCode(code);

        validationRepository.save(validation);
        notificationService.envoyerNotification(validation);
    }

    public Validation getValidationByCode(String code) {
      Optional<Validation> optionalValidation = validationRepository.findByCode(code);
      if (optionalValidation.isEmpty()) {
          throw new RuntimeException("Le code " + code + " n'est pas valide !");
      }
        return optionalValidation.get();
    }
}
