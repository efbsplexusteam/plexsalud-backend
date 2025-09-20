package com.plexsalud.plexsalud.auth.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.plexsalud.plexsalud.user.entities.User;

@Service
public class JwtService {

    // Importa la clave secreta y el tiempo de expiración del JWT desde las
    // propiedades de configuración
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    @Value("${security.jwt.refresh-expiration-time}")
    private long jwtRefreshExpiration;

    // Extrae el nombre de usuario del token JWT
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extrae cualquier claim específico del token JWT utilizando una función
    // proporcionada
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Genera un token JWT con los detalles del usuario y el tiempo de expiración
    // predeterminado
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> extraClaims = new HashMap<>();

        extraClaims.put("profile", ((User) userDetails).getProfile());

        return generateToken(extraClaims, userDetails);
    }

    // Genera un token JWT con los detalles del usuario y cualquier claim adicional
    // especificados
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    /* ----------RefreshToken---------- */
    // Genera un token JWT con los detalles del usuario y el tiempo de expiración
    // predeterminado
    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> extraClaims = new HashMap<>();

        extraClaims.put("profile", ((User) userDetails).getProfile());

        return generateRefreshToken(extraClaims, userDetails);
    }

    // Genera un token JWT con los detalles del usuario y cualquier claim adicional
    // especificados
    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtRefreshExpiration);
    }

    public String generateNewAccessToken(String token) {
        Claims claims = extractAllClaims(token);

        Integer profile = claims.get("profile", Integer.class);

        User user = new User();
        user.setEmail(claims.getSubject());
        user.setProfile(profile);

        return generateToken(user);
    }
    /* ----------RefreshToken---------- */

    // Obtiene el tiempo de expiración del token JWT
    public long getExpirationTime() {
        return jwtExpiration;
    }
    

    // Construye un token JWT con los claims adicionales, detalles del usuario y
    // tiempo de expiración especificados
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration) {
        return Jwts
                .builder()
                .setClaims(extraClaims) // Establece los claims adicionales
                .setSubject(userDetails.getUsername()) // Establece el nombre de usuario del token
                .setIssuedAt(new Date(System.currentTimeMillis())) // Establece la fecha y hora actual como la fecha de
                                                                   // emisión
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Establece la fecha y hora de
                                                                                  // expiración
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Firma el token con una clave secreta utilizando
                                                                    // algoritmo HS256
                .compact(); // Genera el token compacto
    }

    // Valida si el token JWT es válido para un usuario específico
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token); // Extrae el nombre de usuario del token
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token); // Verifica si el nombre de
                                                                                       // usuario coincide y el token no
                                                                                       // ha expirado
    }

    // Verifica si el token JWT ha expirado
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // Compara la fecha de expiración del token con la fecha
                                                            // actual
    }

    // Extrae la fecha y hora de expiración del token JWT
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration); // Utiliza una función para extraer el claim 'exp'
    }

    // Extrae todos los claims del token JWT
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey()) // Establece la clave secreta para validar el token
                .build()
                .parseClaimsJws(token) // Analiza el token y devuelve los claims
                .getBody(); // Obtiene el cuerpo del token que contiene los claims
    }

    // Obtiene la clave secreta para firmar y verificar el token JWT
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey); // Decodifica la clave secreta en bytes
        return Keys.hmacShaKeyFor(keyBytes); // Genera una clave HMAC-SHA utilizando los bytes decodificados
    }
}