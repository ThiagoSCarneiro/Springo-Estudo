package com.thiagocarneiro.estudo.estudospring.dto.usuario;

import com.thiagocarneiro.estudo.estudospring.domain.User;
import com.thiagocarneiro.estudo.estudospring.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCreateDTO(
        @NotBlank @Size(min = 3, max = 150) String name,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres") String password,
        @NotNull UserRole role
) {
    public User toEntity(String encoder) {
        return User.builder()
                .name(name)
                .email(email)
                .password(encoder) // Recebe a senha já com HASH
                .role(role)
                .build();
    }
}
