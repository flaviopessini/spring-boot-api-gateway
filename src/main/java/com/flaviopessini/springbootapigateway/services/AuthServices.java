package com.flaviopessini.springbootapigateway.services;

import com.flaviopessini.springbootapigateway.dtos.v1.AccountCredentialsDTO;
import com.flaviopessini.springbootapigateway.dtos.v1.TokenDTO;
import com.flaviopessini.springbootapigateway.repositories.UserRepository;
import com.flaviopessini.springbootapigateway.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServices {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ResponseEntity<?> signin(AccountCredentialsDTO dto) {
        try {
            final var username = dto.getUserName();
            final var password = dto.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            final var user = userRepository.findByUsername(username);
            var token = new TokenDTO();
            if (user != null) {
                token = tokenProvider.createAccessToken(username, user.getRoles());
            } else {
                throw new UsernameNotFoundException("Username " + username + " not found!");
            }
            return ResponseEntity.ok(token);
        } catch (Exception ex) {
            throw new BadCredentialsException("Invalid username or password!");
        }
    }
}
