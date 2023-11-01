package com.flaviopessini.springbootapigateway.controllers;

import com.flaviopessini.springbootapigateway.dtos.v1.AccountCredentialsDTO;
import com.flaviopessini.springbootapigateway.services.AuthServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@Tag(name = "Authentication endpoint")
public class AuthController {

    @Autowired
    private AuthServices authServices;

    @PostMapping(value = "signin")
    @Operation(summary = "Authenticates a user and returns a token")
    public ResponseEntity<?> signin(@RequestBody AccountCredentialsDTO dto) {
        if (checkParamsIsNotNull(dto)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        }
        final var token = authServices.signin(dto);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        }
        return token;
    }

    @PutMapping(value = "refreshToken/{username}")
    @Operation(summary = "Refresh token for a authenticated user")
    public ResponseEntity<?> refreshToken(@PathVariable("username") String username,
                                          @RequestHeader("Authorization") String refreshToken) {
        if (checkParamsIsNotNull(username, refreshToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        }
        final var token = authServices.refreshToken(username, refreshToken);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        }
        return token;
    }

    private static boolean checkParamsIsNotNull(String username, String refreshToken) {
        return refreshToken == null || refreshToken.isEmpty() || refreshToken.isBlank() ||
                username == null || username.isBlank() || username.isEmpty();
    }

    private static boolean checkParamsIsNotNull(AccountCredentialsDTO dto) {
        return dto == null || dto.getUserName() == null || dto.getUserName().isBlank() ||
                dto.getUserName().isEmpty() || dto.getPassword() == null || dto.getPassword().isBlank() ||
                dto.getPassword().isEmpty();
    }
}
