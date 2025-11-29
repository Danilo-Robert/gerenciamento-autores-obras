package com.danilo.gerenciamento_autores_obras.controller;

import com.danilo.gerenciamento_autores_obras.business.UsuarioService;
import com.danilo.gerenciamento_autores_obras.business.dto.UsuarioDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> criarUsuario(@Valid @RequestBody UsuarioDTO dto){
        return ResponseEntity.ok(usuarioService.criarUsuario(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UsuarioDTO dto){
        return ResponseEntity.ok(usuarioService.autenticarUsuario(dto));
    }
}
