package com.thiagocarneiro.estudo.estudospring.dto.usuario;

import com.thiagocarneiro.estudo.estudospring.domain.User;
import com.thiagocarneiro.estudo.estudospring.enums.UserRole;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String email,
        String password,
        String googleId,
        String avatarUrl,
        UserRole role
) {
    public UserResponseDTO(User user) {
        this(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getGoogleId(),
                user.getAvatarUrl(),
                user.getRole()
        );
    }
}