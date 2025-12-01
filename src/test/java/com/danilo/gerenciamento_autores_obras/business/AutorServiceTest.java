package com.danilo.gerenciamento_autores_obras.business;

import com.danilo.gerenciamento_autores_obras.business.dto.AutorDTO;
import com.danilo.gerenciamento_autores_obras.business.mapper.AutorConverter;
import com.danilo.gerenciamento_autores_obras.infrastructure.model.AutorModel;
import com.danilo.gerenciamento_autores_obras.infrastructure.repository.AutorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutorServiceTest {

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private AutorConverter autorConverter;

    private AutorDTO autorDTO;
    private AutorModel autorModel;

    @Autowired
    @InjectMocks
    private AutorService autorService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);

        autorDTO = AutorDTO.builder()
                .nome("Teste")
                .email("email@test.com")
                .dataNascimento(LocalDate.of(1895, 11, 15))
                .paisOrigem("Brasil")
                .sexo("M")
                .cpf("88844455511")
                .build();

        autorModel = AutorModel.builder()
                .id("12345")
                .nome("Teste")
                .email("email@test.com")
                .dataNascimento(LocalDate.of(1895, 11, 15))
                .paisOrigem("Brasil")
                .sexo("M")
                .cpf("88844455511")
                .obrasIds(new ArrayList<>())
                .build();
    }
    @Test
    void deveCriarAutor() {
        when(autorConverter.paraAutorModel(autorDTO)).thenReturn(autorModel);
        when(autorRepository.save(autorModel)).thenReturn(autorModel);
        when(autorConverter.paraAutorDTO(autorModel)).thenReturn(autorDTO);

        AutorDTO resultado = autorService.criarAutor(autorDTO);

        assertNotNull(resultado);
        assertEquals("Teste", resultado.getNome());
        verify(autorRepository, times(1)).save(autorModel);
    }

    @Test
    void deveAtualizarAutor() {
        AutorDTO novoAutor = AutorDTO.builder()
                .nome("Teste 2")
                .email("email2@test.com")
                .cpf("11122233344")
                .paisOrigem("Estados Unidos")
                .sexo("M")
                .dataNascimento(LocalDate.of(1999,12,12))
                .build();

        when(autorRepository.findById("12345")).thenReturn(Optional.of(autorModel));
        when(autorRepository.save(autorModel)).thenReturn(autorModel);
        when(autorConverter.paraAutorDTO(autorModel)).thenReturn(novoAutor);

        AutorDTO resultado = autorService.atualizarAutor("12345", novoAutor);

        assertNotNull(resultado);
        assertEquals("Teste 2", resultado.getNome());
        verify(autorRepository, times(1)).save(autorModel);
    }

    @Test
    void deveBuscarPorId() {
        when(autorRepository.findById("12345")).thenReturn(Optional.of(autorModel));
        when(autorConverter.paraAutorDTO(autorModel)).thenReturn(autorDTO);

        AutorDTO resultado = autorService.buscarPorId("12345");

        assertNotNull(resultado);
        assertEquals("Teste", resultado.getNome());
    }

    @Test
    void deveListarTodos() {
        when(autorRepository.findAll()).thenReturn(List.of(autorModel));
        when(autorConverter.paraListaAutorDTO(List.of(autorModel))).thenReturn(List.of(autorDTO));

        List<AutorDTO> lista = autorService.listaTodos();

        assertEquals(1, lista.size());
        assertEquals("Teste", lista.get(0).getNome());
    }

    @Test
    void deveDeletarAutor() {
        when(autorRepository.findById("12345")).thenReturn(Optional.of(autorModel));

        autorService.deletarAutor("12345");

        verify(autorRepository, times(1)).delete(autorModel);
    }
}