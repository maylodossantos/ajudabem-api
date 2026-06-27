package com.ajudabem.api.dto.user;

import com.ajudabem.api.domains.user.User;
import com.ajudabem.api.domains.user.UserRole;

import java.time.LocalDateTime;
import java.util.Date;

public record UserResponseDTO (Long id, String name, String email, String phone, String profile_image, String cpf, UserRole role, Date birth_date,
                               LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime last_login_at,
                               Boolean deleted, LocalDateTime deletedAt) {

    public static UserResponseDTO fromEntity(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getProfile_image(),
                user.getCpf(),
                user.getRole(),
                user.getBirth_date(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getLast_login_at(),
                user.getDeleted(),
                user.getDeletedAt()
        );
    }
}
