package com.danilo.gerenciamento_autores_obras.business.mapper;

import com.danilo.gerenciamento_autores_obras.business.dto.AutorDTO;
import com.danilo.gerenciamento_autores_obras.infrastructure.model.AutorModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AutorConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "obrasId", ignore = true)
    AutorModel paraAutorModel(AutorDTO dto);

    AutorDTO paraAutorDTO(AutorModel model);

    List<AutorDTO> paraListaAutorDTO(List<AutorModel> models);
    List<AutorModel> paraListaAutorModel(List<AutorDTO> dtos);
}
