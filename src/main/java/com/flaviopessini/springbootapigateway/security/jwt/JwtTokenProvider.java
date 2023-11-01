package com.flaviopessini.springbootapigateway.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.flaviopessini.springbootapigateway.dtos.v1.TokenDTO;
import com.flaviopessini.springbootapigateway.exceptions.InvalidJwtAuthenticationException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 3600000;

    @Autowired
    private UserDetailsService userDetailsService;

    Algorithm algorithm = null;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    public TokenDTO createAccessToken(String userName, List<String> roles) {
        final var now = new Date();
        final var validity = new Date(now.getTime() + validityInMilliseconds);
        final var accessToken = getAccessToken(userName, roles, now, validity);
        final var refreshToken = getRefreshToken(userName, roles, now);
        return TokenDTO.builder()
                .userName(userName)
                .authenticated(true)
                .created(now)
                .expiration(validity)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public TokenDTO refreshToken(String token) {
        if (token.contains("Bearer ")) {
            token = token.substring("Bearer ".length());
        }
        final var verifier = JWT.require(algorithm).build();
        final var decoded = verifier.verify(token);
        final var username = decoded.getSubject();
        final var roles = decoded.getClaim("roles").asList(String.class);
        return createAccessToken(username, roles);
    }

    private String getAccessToken(String userName, List<String> roles, Date now, Date validity) {
        final var issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withSubject(userName)
                .withIssuer(issuerUrl)
                .sign(algorithm)
                .strip();
    }

    private String getRefreshToken(String userName, List<String> roles, Date now) {
        final var validity = new Date(now.getTime() + (validityInMilliseconds * 3));
        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withSubject(userName)
                .sign(algorithm)
                .strip();
    }

    public Authentication getAuthentication(String token) {
        final var decoder = decodedToken(token);
        final var details = this.userDetailsService.loadUserByUsername(decoder.getSubject());
        return new UsernamePasswordAuthenticationToken(details, "", details.getAuthorities());
    }

    private DecodedJWT decodedToken(String token) {
        final var alg = Algorithm.HMAC256(secretKey.getBytes());
        final var verifier = JWT.require(alg).build();
        return verifier.verify(token);
    }

    public String resolveToken(HttpServletRequest req) {
        final var bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        final var decoded = decodedToken(token);
        try {
            if (decoded.getExpiresAt().before(new Date())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token!");
        }
    }
}
