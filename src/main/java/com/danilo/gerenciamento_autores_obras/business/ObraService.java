package com.danilo.gerenciamento_autores_obras.business;

import com.danilo.gerenciamento_autores_obras.business.dto.ObraDTO;
import com.danilo.gerenciamento_autores_obras.business.mapper.ObraConverter;
import com.danilo.gerenciamento_autores_obras.infrastructure.exceptions.BusinessException;
import com.danilo.gerenciamento_autores_obras.infrastructure.model.AutorModel;
import com.danilo.gerenciamento_autores_obras.infrastructure.model.ObraModel;
import com.danilo.gerenciamento_autores_obras.infrastructure.repository.AutorRepository;
import com.danilo.gerenciamento_autores_obras.infrastructure.repository.ObraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ObraService {

    private final ObraRepository obraRepository;
    private final ObraConverter obraConverter;
    private final AutorRepository autorRepository;

    public ObraDTO criarObra(ObraDTO dto){
        validarAutores(dto.getAutoresId());

        ObraModel model = obraConverter.paraObraModel(dto);
        ObraModel salva = obraRepository.save(model);

        atualizarObrasDosAutores(salva.getAutoresId(), salva.getId(), true);

        return obraConverter.paraObraDTO(salva);
    }

    public void validarAutores(List<String> autoresId){
        if (autoresId == null || autoresId.isEmpty()){
            throw new BusinessException("A obra deve possuir ao menos um ator");
        }
        for (String id : autoresId){
            if (!autorRepository.existsById(id)){
                throw new BusinessException("Autor inválido " + id);
            }
        }
    }

    public void atualizarObrasDosAutores(List<String> autoresId, String obraId, boolean adicionar){
        autoresId.forEach(autorId -> {
            AutorModel autor = autorRepository.findById(autorId).orElseThrow(
                    () -> new BusinessException("Autor não encontrado " + autorId));
            if (adicionar){
                if (!autor.getObrasId().contains(obraId)){
                    autor.getObrasId().add(obraId);
                }
            } else {
                autor.getObrasId().remove(obraId);
            }
            autorRepository.save(autor);
        });
    }

    public ObraDTO atualizarObra(String id, ObraDTO dto){
        ObraModel obraExistente = obraRepository.findById(id).orElseThrow(
                () -> new BusinessException("Obra não encontrada"));
        validarAutores(dto.getAutoresId());

        atualizarObrasDosAutores(obraExistente.getAutoresId(), obraExistente.getId(), false);

        obraExistente.setNome(dto.getNome());
        obraExistente.setDescricao(dto.getDescricao());
        obraExistente.setAutoresId(dto.getAutoresId());
        obraExistente.setDataExposicao(dto.getDataExposicao());
        obraExistente.setDataPublicacao(dto.getDataPublicacao());

        ObraModel salva = obraRepository.save(obraExistente);

        atualizarObrasDosAutores(salva.getAutoresId(), salva.getId(), true);
        return obraConverter.paraObraDTO(salva);
    }

    public ObraDTO buscarPorId(String id){
        ObraModel model = obraRepository.findById(id).orElseThrow(
                () -> new BusinessException("Obra não encontrada"));
        return obraConverter.paraObraDTO(model);
    }

    public List<ObraDTO> listarTodas(){
        return obraConverter.paraListaObraDTO(obraRepository.findAll());
    }

    public void deletaObra(String id){
        ObraModel obra = obraRepository.findById(id).orElseThrow(
                () -> new BusinessException("Obra não encontrada"));

        atualizarObrasDosAutores(obra.getAutoresId(), obra.getId(), false);
        obraRepository.delete(obra);
    }

}
