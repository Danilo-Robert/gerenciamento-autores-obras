package com.danilo.gerenciamento_autores_obras.business.mapper;

import com.danilo.gerenciamento_autores_obras.business.dto.UsuarioDTO;
import com.danilo.gerenciamento_autores_obras.infrastructure.model.UsuarioModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioConverter {

    UsuarioModel paraUsuarioModel(UsuarioDTO usuarioDTO);

    UsuarioDTO paraUsuarioDTO(UsuarioModel usuarioModel);
}
