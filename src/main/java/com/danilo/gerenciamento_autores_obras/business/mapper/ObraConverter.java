package com.danilo.gerenciamento_autores_obras.business.mapper;

import com.danilo.gerenciamento_autores_obras.business.dto.AutorDTO;
import com.danilo.gerenciamento_autores_obras.business.dto.ObraDTO;
import com.danilo.gerenciamento_autores_obras.infrastructure.model.ObraModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ObraConverter {

    @Mapping(target = "id", ignore = true)
    ObraModel paraObraModel(ObraDTO dto);

    ObraDTO paraObraDTO(ObraModel model);

    List<ObraDTO> paraListaObraDTO(List<ObraModel> models);
    List<ObraModel> paraListaObraModel(List<ObraDTO> dtos);
}
