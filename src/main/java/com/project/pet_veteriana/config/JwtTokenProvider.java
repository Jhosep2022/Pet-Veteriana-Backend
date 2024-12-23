package com.project.pet_veteriana.config;

import com.project.pet_veteriana.dto.UsersDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    // Llave secreta generada automáticamente para firma
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Generar el token con los datos de usuario
    public String generateToken(UsersDto usuarioDto) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("rolId", usuarioDto.getRolId());
        claims.put("idUsuario", usuarioDto.getUserId());
        claims.put("correo", usuarioDto.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(usuarioDto.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas de expiración
                .signWith(secretKey)
                .compact();
    }

    // Extraer el nombre de usuario (correo) del token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extraer cualquier claim del token
    public <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extraer todos los claims del token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    // Extraer el ID del usuario (idUsuario) del token
    public Integer extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("idUsuario", Integer.class));  // Extraer el idUsuario
    }

    // Verificar si el token ha expirado
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Validar el token
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}