package com.danilo.gerenciamento_autores_obras.infrastructure.repository;

import com.danilo.gerenciamento_autores_obras.infrastructure.model.ObraModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObraRepository extends MongoRepository<ObraModel, String> {
}
