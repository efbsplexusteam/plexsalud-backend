package com.plexsalud.plexsalud.auth.application.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.plexsalud.plexsalud.auth.application.ports.in.ExtractRoleUseCase;
import com.plexsalud.plexsalud.auth.application.ports.in.ExtractUuidUseCase;
import com.plexsalud.plexsalud.auth.application.ports.in.GenerateNewAccessToken;
import com.plexsalud.plexsalud.auth.application.ports.in.GenerateRefreshTokenUseCase;
import com.plexsalud.plexsalud.auth.application.ports.in.GenerateTokenUseCase;
import com.plexsalud.plexsalud.user.domain.models.Role;
import com.plexsalud.plexsalud.user.domain.models.User;
import com.plexsalud.plexsalud.user.infrastructure.persistance.entities.UserEntity;

@Service
public class JwtService
        implements GenerateTokenUseCase, GenerateRefreshTokenUseCase, GenerateNewAccessToken, ExtractUuidUseCase,
        ExtractRoleUseCase {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    @Value("${security.jwt.refresh-expiration-time}")
    private long jwtRefreshExpiration;

    public String generateTokenUseCase(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUuid(user.uuid());
        userEntity.setEmail(user.email());
        userEntity.setPassword(user.password());
        userEntity.setRole(user.role());
        return generateToken(userEntity);
    }

    public String generateRefreshTokenUseCase(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUuid(user.uuid());
        userEntity.setEmail(user.email());
        userEntity.setPassword(user.password());
        userEntity.setRole(user.role());
        return generateRefreshToken(userEntity);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public UUID extractUuid(HttpServletRequest request) {
        String token = extractJwtFromRequest(request);
        String uuidString = extractClaim(token, claims -> claims.get("uuid", String.class));
        return UUID.fromString(uuidString);
    }

    public Role extractRole(HttpServletRequest request) {
        String token = extractJwtFromRequest(request);
        String roleString = extractClaim(token, claims -> claims.get("role", String.class));
        if (roleString == null) {
            throw new RuntimeException("Role not found in token");
        }
        return Arrays.stream(Role.values())
                .filter(r -> r.name().equalsIgnoreCase(roleString))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Role inválido"));
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> extraClaims = new HashMap<>();

        extraClaims.put("uuid", ((UserEntity) userDetails).getUuid());
        extraClaims.put("role", ((UserEntity) userDetails).getRole());

        return generateToken(extraClaims, userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> extraClaims = new HashMap<>();

        extraClaims.put("uuid", ((UserEntity) userDetails).getUuid());
        extraClaims.put("role", ((UserEntity) userDetails).getRole());

        return generateRefreshToken(extraClaims, userDetails);
    }

    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtRefreshExpiration);
    }

    public HashMap<String, Object> generateNewAccessToken(String token) {
        Claims claims = extractAllClaims(token);

        String uuidString = claims.get("uuid", String.class);
        UUID uuid = UUID.fromString(uuidString);
        String roleString = claims.get("role", String.class);
        Role role = Role.valueOf(roleString);

        UserEntity user = new UserEntity();
        user.setEmail(claims.getSubject()).setRole(role).setUuid(uuid);

        String newToken = generateToken(user);

        HashMap<String, Object> map = new HashMap<>();
        map.put("token", newToken);
        map.put("role", role);

        return map;
    }

    public long getExpirationTime() {
        return jwtExpiration;
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}