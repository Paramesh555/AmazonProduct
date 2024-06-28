package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JWTUtil {

    @Value("${jwt.secret}")
    private static String secretKey;

    private JWTUtil(){

    }

    public static String generateToken(String userName){
        return Jwts
                .builder()
                .subject(userName)
                .expiration(new Date(System.currentTimeMillis() + 3_600_000))
                .signWith(getSecretKey())
                .compact();
    }

    public static boolean validateToken(String token){
        getAllClaimsFromToken(token);
        return true;
    }

    private static Claims getAllClaimsFromToken(String token) {
        try{
            return Jwts
                    .parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }

    }

    public static String extractUsername(String token){
        Claims claims = getAllClaimsFromToken(token);
        return claims.getSubject();
    }

    private static SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

}
