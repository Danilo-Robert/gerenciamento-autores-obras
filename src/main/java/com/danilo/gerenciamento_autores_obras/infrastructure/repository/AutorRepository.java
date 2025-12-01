package com.danilo.gerenciamento_autores_obras.infrastructure.repository;

import com.danilo.gerenciamento_autores_obras.infrastructure.model.AutorModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends MongoRepository<AutorModel, String> {
        boolean existsByEmail(String email);
        boolean existsByCpf(String cpf);
}
