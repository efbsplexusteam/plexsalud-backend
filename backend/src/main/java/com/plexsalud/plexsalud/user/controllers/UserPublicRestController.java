package com.plexsalud.plexsalud.user.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.plexsalud.plexsalud.user.entities.User;
import com.plexsalud.plexsalud.user.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Usuarios", description = "Operaciones con usuarios")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/public/users")
@RestController
public class UserPublicRestController {
    @Autowired
    private final UserService userService;

    public UserPublicRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> allUsers() {
        List<User> users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }

    @PostMapping("/test")
    public ResponseEntity<Void> test(@RequestParam String username, @RequestParam String password) {
        // lógica opcional (crear usuario, etc.)

        ResponseCookie cookie = ResponseCookie.from("token", "valentin" + username + password + "")
                .path("/")
                .domain("plexsalud") // asegúrate que sea el mismo dominio que el frontend
                .httpOnly(true)
                .build();

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .location(URI.create("http://plexsalud:3000/src/index.php"))
                .build();

        // URI redirectUri = URI.create("http://localhost/api/v1/users/" + username +
        // "/" + password); // URL completa de destino
        // return ResponseEntity.status(HttpStatus.FOUND).location(redirectUri).build();
    }

    @GetMapping("test/guard")
    public ResponseEntity<Void> guard(HttpServletRequest request) {

        String c = "";

        // 2. Buscar en Cookies
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    c += cookie.getName();
                    System.out.println(c);
                    return ResponseEntity.status(HttpStatus.FOUND)
                            .location(URI.create("http://plexsalud:3000/src/protected.php?token=" + c))
                            .build();
                }
            }
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("http://plexsalud:3000/src/logout.php"))
                .build();

    }

    // URI redirectUri = URI.create("http://localhost/api/v1/users/" + username +
    // "/" + password); // URL completa de destino
    // return ResponseEntity.status(HttpStatus.FOUND).location(redirectUri).build();

}
