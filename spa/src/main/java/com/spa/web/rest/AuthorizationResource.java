package com.spa.web.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Authorization Resource")
@CrossOrigin
@RestController
public class AuthorizationResource {

    @ApiOperation(value = "login to spa", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully login"),
            @ApiResponse(code = 401, message = "You are not authorized")
    })
    @GetMapping("/login")
    public ResponseEntity login() {
        return ResponseEntity.ok().build();
    }
}
