package com.danilo.gerenciamento_autores_obras.business;

import com.danilo.gerenciamento_autores_obras.business.dto.UsuarioDTO;
import com.danilo.gerenciamento_autores_obras.business.mapper.UsuarioConverter;
import com.danilo.gerenciamento_autores_obras.infrastructure.exceptions.ConflictException;
import com.danilo.gerenciamento_autores_obras.infrastructure.exceptions.UnauthorizedException;
import com.danilo.gerenciamento_autores_obras.infrastructure.model.UsuarioModel;
import com.danilo.gerenciamento_autores_obras.infrastructure.repository.UsuarioRepository;
import com.danilo.gerenciamento_autores_obras.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UsuarioConverter usuarioConverter;
    private final AuthenticationManager authenticationManager;

    public UsuarioDTO criarUsuario(UsuarioDTO dto){
        emailExiste(dto.getEmail());
        dto.setSenha(passwordEncoder.encode(dto.getSenha()));
        UsuarioModel model = usuarioConverter.paraUsuarioModel(dto);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(model));
    }

    public String autenticarUsuario(UsuarioDTO dto){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(),
                            dto.getSenha())
            );
            return "Bearer " + jwtUtil.generateToken(authentication.getName());
        } catch (BadCredentialsException | UsernameNotFoundException | AuthorizationDeniedException e){
            throw new UnauthorizedException("Usuário ou senha inválidos", e.getCause());
        }
    }

    public void emailExiste(String email){
        try{
            boolean existe = verificaEmailExistente(email);
            if (existe){
                throw new ConflictException("E-mail já cadastrado" + email);
            }
        }catch (ConflictException e){
            throw new ConflictException("E-mail já cadastrado", e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }
}