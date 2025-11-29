package com.danilo.gerenciamento_autores_obras.infrastructure.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UsuarioModel {

    @Id
    private String id;
    private String email;
    private String senha;
}
