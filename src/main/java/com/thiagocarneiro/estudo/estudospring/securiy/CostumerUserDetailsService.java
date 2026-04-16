package com.thiagocarneiro.estudo.estudospring.securiy;

import com.thiagocarneiro.estudo.estudospring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@RequiredArgsConstructor
public class CostumerUserDetailsService implements UserDetailsService {

    private final UserService service;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = service.findByLogin(email);

        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        var authorities = user.authority().stream()
                .map(auth -> new SimpleGrantedAuthority(auth.name())) // 'READ', 'CREATE', etc.
                .toList();

        return User.builder()
                .username(user.login())
                .password(user.password())
                .authorities(authorities)
                .build();
    }
}
