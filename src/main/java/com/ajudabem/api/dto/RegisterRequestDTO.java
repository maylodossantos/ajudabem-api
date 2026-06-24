package com.ajudabem.api.dto;

import com.ajudabem.api.domains.user.UserRole;

public record RegisterRequestDTO (String name, String email, String phone, String password) { }
