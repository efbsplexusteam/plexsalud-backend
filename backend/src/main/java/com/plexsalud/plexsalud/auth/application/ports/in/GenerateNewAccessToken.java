package com.plexsalud.plexsalud.auth.application.ports.in;

import java.util.HashMap;

public interface GenerateNewAccessToken {
    HashMap<String, Object> generateNewAccessToken(String token);
}
