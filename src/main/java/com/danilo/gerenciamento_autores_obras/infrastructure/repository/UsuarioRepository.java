package com.danilo.gerenciamento_autores_obras.infrastructure.repository;

import com.danilo.gerenciamento_autores_obras.infrastructure.model.UsuarioModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<UsuarioModel, String> {
    Optional<UsuarioModel> findByEmail(String email);
    boolean existsByEmail(String email);
}
