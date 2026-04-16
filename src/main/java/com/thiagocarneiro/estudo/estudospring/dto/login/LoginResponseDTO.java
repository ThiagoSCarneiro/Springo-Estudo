package com.thiagocarneiro.estudo.estudospring.dto.login;

import com.thiagocarneiro.estudo.estudospring.domain.User;
import com.thiagocarneiro.estudo.estudospring.enums.UserAuthority;
import com.thiagocarneiro.estudo.estudospring.enums.UserRole;

import java.util.List;

public record LoginResponseDTO(
        String login,
        String password,
        List<UserAuthority> authority
) {
    public LoginResponseDTO(User user){
        this(
                user.getEmail(),
                user.getPassword(),
                user.getAuthority()
        );
    }
}
