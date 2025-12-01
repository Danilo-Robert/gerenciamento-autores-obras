package com.danilo.gerenciamento_autores_obras.business;

import com.danilo.gerenciamento_autores_obras.business.dto.UsuarioDTO;
import com.danilo.gerenciamento_autores_obras.business.mapper.UsuarioConverter;
import com.danilo.gerenciamento_autores_obras.infrastructure.model.UsuarioModel;
import com.danilo.gerenciamento_autores_obras.infrastructure.repository.UsuarioRepository;
import com.danilo.gerenciamento_autores_obras.infrastructure.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UsuarioConverter usuarioConverter;

    private UsuarioDTO usuarioDTO;
    private UsuarioModel usuarioModel;

    @Autowired
    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);

        usuarioDTO = UsuarioDTO.builder()
                .email("email@test.com")
                .senha("123")
                .build();

        usuarioModel = UsuarioModel.builder()
                .id("12345")
                .email("email@test.com")
                .senha("criptografada")
                .build();
    }


    @Test
    void deveCriarUsuarioComSenhaCriptografada() {
        when(passwordEncoder.encode("123")).thenReturn("criptografada");
        when(usuarioConverter.paraUsuarioModel(usuarioDTO)).thenReturn(usuarioModel);
        when(usuarioRepository.save(usuarioModel)).thenReturn(usuarioModel);
        when(usuarioConverter.paraUsuarioDTO(usuarioModel)).thenReturn(usuarioDTO);

        UsuarioDTO resultado = usuarioService.criarUsuario(usuarioDTO);

        assertNotNull(resultado);
        verify(passwordEncoder).encode("123");
        verify(usuarioRepository).save(usuarioModel);
    }

    @Test
    void deveAutenticarUsuario() {
        Authentication authenticationMock = mock(Authentication.class);

        when(authenticationManager.authenticate(
                any(UsernamePasswordAuthenticationToken.class))
        ).thenReturn(authenticationMock);

    when(authenticationMock.getName()).thenReturn("email@test.com");

        when(jwtUtil.generateToken("email@test.com")).thenReturn("TOKEN111");

        String token = usuarioService.autenticarUsuario(usuarioDTO);

        assertEquals("Bearer TOKEN111", token);
    }
}