package com.plexsalud.plexsalud.auth.application.ports.in;

import com.plexsalud.plexsalud.user.domain.entities.Role;

import jakarta.servlet.http.HttpServletRequest;

public interface ExtractRoleUseCase {
    Role extractRole(HttpServletRequest request);
}
