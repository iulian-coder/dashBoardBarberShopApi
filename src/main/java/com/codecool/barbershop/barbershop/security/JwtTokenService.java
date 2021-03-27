package com.codecool.barbershop.barbershop.security;

import com.codecool.barbershop.barbershop.user.UserPrincipal;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenService {

    @Value("${TOKEN_SECRET:Default}")
    private String tokenSecret;


    private SecretKey getSecretKey() {
        return
                Keys.hmacShaKeyFor(tokenSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String userId) {
        long tokenExpirationTime = 18000000; // 300 minutes

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + tokenExpirationTime);

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //    TODO refresh-token
    public String createRefreshToken(String userId) {
//       7 day
        return null;
    }

    public Long getUserIdFromToken(String token) {
        String userId = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token).getBody().getSubject();
        return Long.parseLong(userId);
    }

    public boolean validateToken(String authToken) {

        try {
            Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
