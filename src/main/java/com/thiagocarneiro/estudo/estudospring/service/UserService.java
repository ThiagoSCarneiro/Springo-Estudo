package com.thiagocarneiro.estudo.estudospring.service;

import com.thiagocarneiro.estudo.estudospring.domain.User;
import com.thiagocarneiro.estudo.estudospring.dto.usuario.UserCreateDTO;
import com.thiagocarneiro.estudo.estudospring.dto.usuario.UserCreateGoogleDTO;
import com.thiagocarneiro.estudo.estudospring.dto.usuario.UserResponseDTO;
import com.thiagocarneiro.estudo.estudospring.dto.usuario.UserUpdateDTO;
import com.thiagocarneiro.estudo.estudospring.enums.UserRole;
import com.thiagocarneiro.estudo.estudospring.exception.BusinessException;
import com.thiagocarneiro.estudo.estudospring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    @Transactional
    public UserResponseDTO saveGoogle(UserCreateGoogleDTO dto) {
        var user = dto.toEntity(UserRole.USER_PESSOAL);
        var userSave = repository.save(user);
        return new UserResponseDTO(userSave);
    }

    @Transactional
    public UserResponseDTO save(UserCreateDTO dto) {
        var user = dto.toEntity(dto.password().toUpperCase());
        if(repository.existsByEmail(user.getEmail())) {
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
        var user = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário com id: "+ id + "\n não localizado."));
        return new UserResponseDTO(user);
    }

    @Transactional
    public UserResponseDTO partialUpdate(UUID id, UserUpdateDTO dto) {
        User user = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário com id: "+ id + "\n não localizado."));
        User userUpdate = user.toBuilder()
                .name(dto.name() != null ? dto.name() : user.getName())
                .email(dto.email() != null ? dto.email() : user.getEmail())
                .password(dto.password() != null ? dto.password() : user.getPassword())
                .avatarUrl(dto.avatarUrl() != null ? dto.avatarUrl() : user.getAvatarUrl())
                .role(dto.role() != null ? dto.role() : user.getRole())
                        .build();

                repository.save(userUpdate);
        return new UserResponseDTO(userUpdate);
    }

    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }


}
