package com.plexsalud.plexsalud.nurse.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NurseNotFoundException extends RuntimeException {
    public NurseNotFoundException(String message) {
        super(message);
    }
}