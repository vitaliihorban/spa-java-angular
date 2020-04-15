package com.spa.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class AuthorizationResource {

    @GetMapping("/login")
    public ResponseEntity login() {
        return ResponseEntity.ok().build();
    }
}
