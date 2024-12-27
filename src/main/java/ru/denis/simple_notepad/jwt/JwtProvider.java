package ru.denis.simple_notepad.jwt;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.denis.simple_notepad.exception.JwtAuthenticationException;
import ru.denis.simple_notepad.model.Person;
import ru.denis.simple_notepad.service.PeopleService;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {
    @Value("${jwt.header}")
    private String header;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private long expiration;


    public String createToken(UserDetails userDetails) {
        Map<String, String> claims = new HashMap<>();
        claims.put("iss", "http://secure.genuinecider.com");

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(expiration * 1000)))
                .signWith(generateKey())
                .compact();
    }

    private SecretKey generateKey() {
        byte[] decodedKey = Base64.getDecoder().decode(secret);

        return Keys.hmacShaKeyFor(decodedKey);
    }

    public String extractUsername(String jwt) {
        Claims claims = getClaims(jwt);

        return claims.getSubject();
    }

    public boolean validateToken(String jwt) {
        Claims claims = getClaims(jwt);

        return claims.getExpiration().after(new Date());
    }

    public Claims getClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }
}
