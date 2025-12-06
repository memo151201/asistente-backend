package com.proyecto.asistente_backend.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration:86400000}") // 24 horas por defecto
    private Long expiration;

    public String generarToken(String email, Long usuarioId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("usuarioId", usuarioId);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }


    // Extraer usuario ID del token
    public Long extraerUsuarioId(String token) {
        Claims claims = extraerClaims(token);
        Object usuarioIdObj = claims.get("usuarioId");

        if (usuarioIdObj instanceof Integer) {
            return ((Integer) usuarioIdObj).longValue();
        } else if (usuarioIdObj instanceof Long) {
            return (Long) usuarioIdObj;
        }

        throw new RuntimeException("No se pudo extraer usuarioId del token");
    }

    // Extraer email del token
    public String extraerEmail(String token) {
        return extraerClaims(token).getSubject();
    }

    // Validar token
    public boolean validarToken(String token, String email) {
        final String emailToken = extraerEmail(token);
        return (emailToken.equals(email) && !isTokenExpirado(token));
    }

    // Verificar si el token está expirado
    private boolean isTokenExpirado(String token) {
        return extraerExpiracion(token).before(new Date());
    }

    // Extraer fecha de expiración
    private Date extraerExpiracion(String token) {
        return extraerClaims(token).getExpiration();
    }

    // Extraer todos los claims
    private Claims extraerClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Obtener clave de firma
    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}