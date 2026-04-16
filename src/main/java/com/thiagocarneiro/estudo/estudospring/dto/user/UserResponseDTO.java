package com.thiagocarneiro.estudo.estudospring.dto.user;

import com.thiagocarneiro.estudo.estudospring.domain.User;
import com.thiagocarneiro.estudo.estudospring.enums.UserAuthority;
import com.thiagocarneiro.estudo.estudospring.enums.UserRole;

import java.util.List;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String email,
        String password,
        String googleId,
        String avatarUrl,
        UserRole role,
        List<UserAuthority> authority
) {
    public UserResponseDTO(User user) {
        this(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getGoogleId(),
                user.getAvatarUrl(),
                user.getRole(),
                user.getAuthority()
        );
    }
}