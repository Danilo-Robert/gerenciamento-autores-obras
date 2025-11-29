package com.danilo.gerenciamento_autores_obras.business;

import com.danilo.gerenciamento_autores_obras.business.dto.AutorDTO;
import com.danilo.gerenciamento_autores_obras.business.mapper.AutorConverter;
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
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorConverter autorConverter;
    private final ObraRepository obraRepository;


    public AutorDTO criarAutor(AutorDTO dto){
        if (autorRepository.existsByEmail(dto.getEmail())){
            throw new BusinessException("E-mail já cadastrado");
        }

        if (dto.getCpf() != null && autorRepository.existsByCpf(dto.getCpf())){
            throw new BusinessException("CPF já cadastrado");
        }

        return autorConverter.paraAutorDTO(autorRepository.save(autorConverter.paraAutorModel(dto)));
    }

    public AutorDTO atualizarAutor(String id, AutorDTO dto){
        AutorModel autor = autorRepository.findById(id).orElseThrow(
                () -> new BusinessException("Autor não encontrado"));
        if (!autor.getEmail().equals(dto.getEmail()) && autorRepository.existsByEmail(dto.getEmail())){
            throw new BusinessException("E-mail já cadastrado");
        }
        if (dto.getCpf() != null && !dto.getCpf().equals(autor.getCpf()) && autorRepository.existsByCpf(dto.getCpf())){
            throw new BusinessException("CPF já cadastrado");
        }

        autor.setNome(dto.getNome());
        autor.setSexo(dto.getSexo());
        autor.setEmail(dto.getEmail());
        autor.setCpf(dto.getCpf());
        autor.setPaisOrigem(dto.getPaisOrigem());
        autor.setDataNascimento(dto.getDataNascimento());

        AutorModel salvo = autorRepository.save(autor);
        return autorConverter.paraAutorDTO(salvo);
    }

    public AutorDTO buscarPorId(String id){
        AutorModel model = autorRepository.findById(id).orElseThrow(
                () -> new BusinessException("Autor não encontrado"));
        return autorConverter.paraAutorDTO(model);
    }

    public List<AutorDTO> listaTodos(){
        return autorConverter.paraListaAutorDTO(autorRepository.findAll());
    }

    public void deletarAutor(String id){
        AutorModel autor = autorRepository.findById(id).orElseThrow(
                () -> new BusinessException("Autor não encontrado"));

        if (!autor.getObrasIds().isEmpty()){
            autor.getObrasIds().forEach(obraId -> {
                ObraModel obra = obraRepository.findById(obraId).orElseThrow(
                        () -> new BusinessException("Obra não encontrada ao remover autor"));
            obra.getAutoresIds().remove(autor.getId());
            obraRepository.save(obra);
            });
        }
        autorRepository.delete(autor);
    }
}