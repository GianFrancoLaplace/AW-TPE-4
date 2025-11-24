package org.example.microservicioidentidadyauth.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.microservicioidentidadyauth.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME;

    // Obtener la clave de firma
    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Extraer username (email) del token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extraer fecha de expiración
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extraer un claim específico
    public <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extraer todos los claims
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())  // CAMBIO en 0.12.5
                .build()
                .parseSignedClaims(token)  // CAMBIO en 0.12.5
                .getPayload();
    }

    // Verificar si el token expiró
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Validar token
    public Boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Validar token con username
    public Boolean validateToken(String token, String username) {
        final String usernameFromToken = extractUsername(token);
        return (usernameFromToken.equals(username) && !isTokenExpired(token));
    }

    // Generar token con solo el username (email)
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    // Generar token con toda la información del usuario
    public String generateToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", usuario.getId());
        claims.put("nombre", usuario.getNombre());
        claims.put("email", usuario.getEmail());

        // Si tienes el campo rol descomentado:
        if (usuario.getRol() != null) {
            claims.put("rol", usuario.getRol().toString());
        }

        return createToken(claims, usuario.getEmail());
    }

    // Crear el token JWT
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))  // CAMBIO en 0.12.5
                .signWith(getSignKey())  // CAMBIO en 0.12.5
                .compact();
    }

    // Métodos útiles para extraer información del token
    public Long getUserIdFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("id", Long.class);
    }

    public String getEmailFromToken(String token) {
        return extractUsername(token);
    }

    public String getNombreFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("nombre", String.class);
    }

    public String getRolFromToken(String token) {
        Claims claims = extractAllClaims(token);
        Object rol = claims.get("rol");
        return rol != null ? rol.toString() : "USER";
    }

    // Obtener Authentication de Spring Security desde el token
    public Authentication getAuthentication(String token) {
        Claims claims = extractAllClaims(token);
        String email = claims.getSubject();
        String rol = getRolFromToken(token);

        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(rol)
        );

        return new UsernamePasswordAuthenticationToken(email, null, authorities);
    }
}