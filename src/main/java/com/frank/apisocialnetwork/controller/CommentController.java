package com.frank.apisocialnetwork.controller;

import com.frank.apisocialnetwork.dto.CommentDTO;
import com.frank.apisocialnetwork.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@AllArgsConstructor
@RestController
@RequestMapping(path = "comment")
public class CommentController {

    private CommentService commentService;

    @PostMapping(path = "add")
    public ResponseEntity<String> addComment(@RequestBody CommentDTO comment) {
        log.info(comment.toString());
        commentService.addComment(comment);
        return new ResponseEntity<>("Comment created", HttpStatus.CREATED);
    }
}
