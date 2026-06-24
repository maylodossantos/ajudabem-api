package com.ajudabem.api.dto;

import com.ajudabem.api.domains.user.UserRole;

import java.util.Date;

public record UserResponseDTO (Long id, String name, String email, String phone, String profile_image, String cpf, UserRole role, Date birth_date) { }
