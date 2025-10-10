package com.plexsalud.plexsalud.auth.application.ports.in;

import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;

public interface ExtractUuidUseCase {
    UUID extractUuid(HttpServletRequest request);
}
