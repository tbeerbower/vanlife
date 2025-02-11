package org.beerbower.vanlife.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.beerbower.vanlife.security.TokenClaims;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

// JwtService.java
@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret;

    public TokenClaims validateToken(String token) {
        Key key = new SecretKeySpec(jwtSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return new TokenClaims(
                claims.get("sub", String.class),
                claims.get("exp", Date.class),
                claims.get("iat", Date.class),
                claims.get("iss", String.class),
                claims.get("auth", String.class)
        );
    }
}
