package com.frank.apiavisutilisateurs.controller;

import com.frank.apiavisutilisateurs.entity.Avis;
import com.frank.apiavisutilisateurs.service.AvisService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(path = "avis")
public class AvisController {

    private final AvisService avisService;

    @PostMapping(path = "creer")
    public ResponseEntity<Avis> creerAvis(@RequestBody Avis avis){
         return avisService.creerAvis(avis);
    }
}
