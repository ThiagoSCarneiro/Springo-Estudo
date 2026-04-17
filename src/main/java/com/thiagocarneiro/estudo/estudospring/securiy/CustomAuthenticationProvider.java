package com.thiagocarneiro.estudo.estudospring.securiy;

import com.thiagocarneiro.estudo.estudospring.dto.user.UserResponseDTO;
import com.thiagocarneiro.estudo.estudospring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserResponseDTO dto = userService.findByLogin(login);

        if (dto == null) {
            throw getErrorUsernameNotFound();
        }

        String passwordEnconde = dto.password();
        if(passwordEncoder.matches(password, passwordEnconde)) {
            return new CustomAuthentication(dto);

        }
        throw getErrorUsernameNotFound();
    }

    private  UsernameNotFoundException getErrorUsernameNotFound() {
        return new UsernameNotFoundException("Usuario e/ou senha incorreto!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
