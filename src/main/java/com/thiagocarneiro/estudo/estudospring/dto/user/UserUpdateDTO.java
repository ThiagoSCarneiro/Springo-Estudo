package com.thiagocarneiro.estudo.estudospring.dto.user;

import com.thiagocarneiro.estudo.estudospring.enums.UserAuthority;
import com.thiagocarneiro.estudo.estudospring.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record UserUpdateDTO(
        @Size(min = 3, max = 150) String name,
        @Email String email,
        String password,
        String avatarUrl,
        List<UserAuthority> userAuthority
) {

}
