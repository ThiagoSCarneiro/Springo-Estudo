package com.thiagocarneiro.estudo.estudospring.dto.user;

import com.thiagocarneiro.estudo.estudospring.domain.User;
import com.thiagocarneiro.estudo.estudospring.enums.UserAuthority;
import com.thiagocarneiro.estudo.estudospring.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UserCreateGoogleDTO(
        @NotBlank @Size(min = 3, max = 150) String name,
        @NotBlank @Email String email,
        String googleId,
        String avatarUrl
) {
    public User toEntity(UserRole roleUser, List<UserAuthority> authorities) {
        return User.builder()
                .name(name)
                .email(email)
                .googleId(googleId)
                .avatarUrl(avatarUrl)
                .role(roleUser)
                .authority(authorities)
                .build();
    }
}
