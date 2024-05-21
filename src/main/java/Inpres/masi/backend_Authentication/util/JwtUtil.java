package Inpres.masi.backend_Authentication.util;

import Inpres.masi.backend_Authentication.Crypto.KeyStoreLoader;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtUtil {
    @Value("${jwt.secret}")
    private String SECRET;
    private Key key;

    public JwtUtil() throws Exception {
        this.key = new KeyStoreLoader("src/main/resources/Key/backendAuth.p12", "P@ssw0rd").getPrivateKey("backendAuth");
    }

    public String generateToken(String username) {
        // 12 Hours
        long EXPIRATION_TIME = 1000L * 60 * 60 * 12;
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.RS256, this.key)
                .compact();
    }
    public String extractUsername(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(this.key)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }
    public boolean isTokenValid(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(this.key)
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
