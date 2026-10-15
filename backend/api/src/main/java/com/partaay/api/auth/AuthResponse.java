package com.partaay.api.auth;
import java.util.UUID;
public record AuthResponse(UUID id, String name, String email) { static AuthResponse from(UserEntity u){ return new AuthResponse(u.getId(),u.getDisplayName(),u.getEmail()); } }
