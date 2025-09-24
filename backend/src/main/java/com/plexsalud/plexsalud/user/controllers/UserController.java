package com.plexsalud.plexsalud.user.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plexsalud.plexsalud.user.dtos.UserDto;
import com.plexsalud.plexsalud.user.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Users", description = "Operations with users")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<UserDto> getAllUsers() {
        UserDto user1 = new UserDto("Francisco");
        UserDto user2 = new UserDto("Michelle");
        UserDto user3 = new UserDto("Abdon");

        List<UserDto> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        return users;
    }

}
