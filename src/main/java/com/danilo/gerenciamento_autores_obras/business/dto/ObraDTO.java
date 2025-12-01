package com.danilo.gerenciamento_autores_obras.business.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ObraDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Size(max = 240, message = "A descrição pode ter no máximo 240 caracteres")
    private String descricao;

    private LocalDate dataPublicacao;
    private LocalDate dataExposicao;

    private List<String> autoresId;
}
