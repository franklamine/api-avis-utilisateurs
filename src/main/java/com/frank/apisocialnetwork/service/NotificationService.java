package com.frank.apisocialnetwork.service;

import com.frank.apisocialnetwork.entity.Validation;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificationService {

    JavaMailSender javaMailSender;
    public void envoyerNotification(Validation validation) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@frank.tech");
        message.setTo(validation.getUtilisateur().getEmail());
        message.setSubject("Code d'activation");
        message.setText("Bonjour votre code d'activation est: " + validation.getCode());

        javaMailSender.send(message);
    }
}
