package com.danilo.gerenciamento_autores_obras.infrastructure.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("autor")
public class AutorModel {

    @Id
    private String id;

    @NotBlank
    private String nome;

    private String sexo;

    @Email
    @NotBlank
    private String email;

    @NotNull
    private LocalDate dataNascimento;

    @NotBlank
    private String paisOrigem;

    private String cpf;

    private List<String> obrasIds = new ArrayList<>();
}
