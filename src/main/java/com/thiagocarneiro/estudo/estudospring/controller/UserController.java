package com.thiagocarneiro.estudo.estudospring.controller;

import com.thiagocarneiro.estudo.estudospring.controller.util.UriGenerator;
import com.thiagocarneiro.estudo.estudospring.dto.user.UserCreateDTO;
import com.thiagocarneiro.estudo.estudospring.dto.user.UserCreateGoogleDTO;
import com.thiagocarneiro.estudo.estudospring.dto.user.UserResponseDTO;
import com.thiagocarneiro.estudo.estudospring.dto.user.UserUpdateDTO;
import com.thiagocarneiro.estudo.estudospring.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping(value = "/google")
    public ResponseEntity<UserResponseDTO> saveGoogle(@RequestBody @Valid UserCreateGoogleDTO dto){
        var response = service.saveGoogle(dto);
        return ResponseEntity.created(UriGenerator.generateFromUuid(response.id())).body(response);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('CREATE')")
    public ResponseEntity<UserResponseDTO> save(@RequestBody @Valid UserCreateDTO dto){
        var response = service.save(dto);
        return ResponseEntity.created(UriGenerator.generateFromUuid(response.id())).body(response);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('READ')")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable UUID id){
        var response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('READ')")
    public ResponseEntity<List<UserResponseDTO>> list(){
        return ResponseEntity.ok(service.list());
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('DELETE')")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('UPDATE')")
    public ResponseEntity<UserResponseDTO> partialUpdate(@PathVariable UUID id, @RequestBody UserUpdateDTO dto){
        var response = service.partialUpdate(id, dto);
        return ResponseEntity.ok(response);
    }

}
