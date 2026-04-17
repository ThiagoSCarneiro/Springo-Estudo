package com.thiagocarneiro.estudo.estudospring.service;

import com.thiagocarneiro.estudo.estudospring.domain.User;
import com.thiagocarneiro.estudo.estudospring.dto.user.UserCreateDTO;
import com.thiagocarneiro.estudo.estudospring.dto.user.UserCreateGoogleDTO;
import com.thiagocarneiro.estudo.estudospring.dto.user.UserResponseDTO;
import com.thiagocarneiro.estudo.estudospring.dto.user.UserUpdateDTO;
import com.thiagocarneiro.estudo.estudospring.enums.UserAuthority;
import com.thiagocarneiro.estudo.estudospring.enums.UserRole;
import com.thiagocarneiro.estudo.estudospring.exception.BusinessException;
import com.thiagocarneiro.estudo.estudospring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Transactional
    public UserResponseDTO saveGoogle(UserCreateGoogleDTO dto) {
        List<UserAuthority> authorities = new ArrayList<>(Arrays.stream(UserAuthority.values()).toList());
        var user = dto.toEntity(UserRole.PERSONAL_USER, authorities);
        var userSave = repository.save(user);
        return new UserResponseDTO(userSave);
    }

    @Transactional
    public UserResponseDTO getOrCreateLoginGoogle(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        return repository.findByEmail(email)
                .map(UserResponseDTO::new)
                .orElseGet(() -> {
                    var user = User.builder()
                            .email(email)
                            .name(oAuth2User.getAttribute("name"))
                            .googleId(oAuth2User.getAttribute("sub"))
                            .avatarUrl(oAuth2User.getAttribute("picture"))
                            .build();
                    repository.save(user);
                    return new UserResponseDTO(user);
                });
    }

    @Transactional
    public UserResponseDTO save(UserCreateDTO dto) {
        var user = dto.toEntity(encoder.encode(dto.password()), UserRole.BUSINESS_USER);
        if (repository.existsByEmail(user.getEmail())) {
            throw new BusinessException("This email is already registered in our system.");
        }
        var userSave = repository.save(user);
        return new UserResponseDTO(userSave);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> list() {
        return repository.findAll().stream().map(UserResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findById(UUID id) {
        var user = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário com id: " + id + "\n não localizado."));
        return new UserResponseDTO(user);
    }

    @Transactional
    public UserResponseDTO partialUpdate(UUID id, UserUpdateDTO dto) {
        var user = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário com id: " + id + "\n não localizado."));
        var userUpdate = user.toBuilder()
                .name(dto.name() != null ? dto.name() : user.getName())
                .email(dto.email() != null ? dto.email() : user.getEmail())
                .password(dto.password() != null ? encoder.encode(dto.password()) : user.getPassword())
                .avatarUrl(dto.avatarUrl() != null ? dto.avatarUrl() : user.getAvatarUrl())
                .authority(dto.userAuthority() != null ? dto.userAuthority().stream().toList() : user.getAuthority())
                .build();

        repository.save(userUpdate);
        return new UserResponseDTO(userUpdate);
    }

    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }


    public UserResponseDTO findByLogin(String email) {
        return repository.findByEmail(email)
                .map(UserResponseDTO::new)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + "email"));
    }
}
