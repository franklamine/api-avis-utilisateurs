package com.frank.apisocialnetwork.controller;

import com.frank.apisocialnetwork.entity.Publication;
import com.frank.apisocialnetwork.service.PublicationService;
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

    private final PublicationService avisService;

    @PostMapping(path = "creer")
    public ResponseEntity<Publication> creerAvis(@RequestBody Publication avis){
         return avisService.creerAvis(avis);
    }
}
