package com.danilo.gerenciamento_autores_obras.business.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AutorDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    private String sexo;

    @Email(message = "E-mail é obrigatório")
    @NotBlank(message = "E-mail é obrigatório")
    private String email;

    @NotNull(message = "Data de nascimento é obrigatório")
    private LocalDate dataNascimento;

    @NotBlank(message = "País de origem é obrigatório")
    private String paisOrigem;

    private String cpf;
}
