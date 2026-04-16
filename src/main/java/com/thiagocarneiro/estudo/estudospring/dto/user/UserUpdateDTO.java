package com.thiagocarneiro.estudo.estudospring.dto.user;

import com.thiagocarneiro.estudo.estudospring.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UserUpdateDTO(
        @NotBlank UUID id,
        @Size(min = 3, max = 150) String name,
        @Email String email,
        String password,
        String avatarUrl,
        UserRole role
) {

}
