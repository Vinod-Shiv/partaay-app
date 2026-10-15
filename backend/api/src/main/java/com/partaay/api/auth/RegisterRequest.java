package com.partaay.api.auth;
import jakarta.validation.constraints.*;
public record RegisterRequest(@NotBlank @Size(max=120) String name, @NotBlank @Email @Size(max=320) String email, @NotBlank @Size(min=8,max=72) String password) {}
