package com.danilo.gerenciamento_autores_obras.infrastructure.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("obra")
public class ObraModel {

    @Id
    private String id;

    @NotBlank
    private String nome;

    @Size(max = 240)
    private String descricao;


    private LocalDate dataPublicacao;

    private LocalDate dataExposicao;

    private List<String> autoresId = new ArrayList<>();

}
