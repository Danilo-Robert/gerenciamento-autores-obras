package com.danilo.gerenciamento_autores_obras.business;

import com.danilo.gerenciamento_autores_obras.business.dto.ObraDTO;
import com.danilo.gerenciamento_autores_obras.business.mapper.ObraConverter;
import com.danilo.gerenciamento_autores_obras.infrastructure.model.AutorModel;
import com.danilo.gerenciamento_autores_obras.infrastructure.model.ObraModel;
import com.danilo.gerenciamento_autores_obras.infrastructure.repository.AutorRepository;
import com.danilo.gerenciamento_autores_obras.infrastructure.repository.ObraRepository;
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

class ObraServiceTest {

    @Mock
    private ObraRepository obraRepository;

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private ObraConverter obraConverter;

    private ObraDTO obraDTO;
    private ObraModel obraModel;

    private AutorModel autorModel;

    @Autowired
    @InjectMocks
    private ObraService obraService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);

        autorModel = AutorModel.builder()
                .id("111")
                .nome("Autor Teste")
                .email("autor@test.com")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .paisOrigem("Brasil")
                .sexo("M")
                .cpf("12345678999")
                .obrasIds(new ArrayList<>())
                .build();

        obraDTO = ObraDTO.builder()
                .nome("Obra Teste")
                .descricao("Descrição teste da obra")
                .dataPublicacao(LocalDate.of(2020, 5, 10))
                .dataExposicao(LocalDate.of(2021, 1, 20))
                .autoresId(List.of("111"))
                .build();

        obraModel = ObraModel.builder()
                .id("111")
                .nome("Obra Teste")
                .descricao("Descrição teste da obra")
                .dataPublicacao(LocalDate.of(2020, 5, 10))
                .dataExposicao(LocalDate.of(2021, 1, 20))
                .autoresIds(new ArrayList<>(List.of("111")))
                .build();
    }
    @Test
    void deveCriarObra() {
        when(autorRepository.existsById("111")).thenReturn(true);
        when(autorRepository.findById("111")).thenReturn(Optional.of(autorModel));
        when(obraConverter.paraObraModel(obraDTO)).thenReturn(obraModel);
        when(obraRepository.save(obraModel)).thenReturn(obraModel);
        when(obraConverter.paraObraDTO(obraModel)).thenReturn(obraDTO);

        ObraDTO resultado = obraService.criarObra(obraDTO);

        assertNotNull(resultado);
        assertEquals("Obra Teste", resultado.getNome());
        verify(obraRepository, times(1)).save(obraModel);
    }

    @Test
    void deveValidarAutores() {
        when(autorRepository.existsById("111")).thenReturn(true);

        assertDoesNotThrow(() -> obraService.validarAutores(List.of("111")));

        verify(autorRepository, times(1)).existsById("111");
    }

    @Test
    void deveAtualizarObrasDosAutores() {
        when(autorRepository.findById("111")).thenReturn(Optional.of(autorModel));

        assertDoesNotThrow(() -> obraService.atualizarObrasDosAutores(
                obraModel.getAutoresIds(),
                obraModel.getId(),
                true
        ));

        assertTrue(autorModel.getObrasIds().contains("111"));
        verify(autorRepository, times(1)).save(autorModel);
    }

    @Test
    void deveAtualizarObra() {
        obraDTO.setNome("Obra Atualizada");
        obraDTO.setAutoresId(List.of("111"));
        obraModel.setAutoresIds(List.of("111"));
        when(obraRepository.existsById("111")).thenReturn(true);
        when(obraRepository.findById("111")).thenReturn(Optional.of(obraModel));
        when(autorRepository.existsById("111")).thenReturn(true);
        when(autorRepository.findById("111")).thenReturn(Optional.of(autorModel));
        when(obraConverter.paraObraModel(obraDTO)).thenReturn(obraModel);
        when(obraRepository.save(obraModel)).thenReturn(obraModel);
        when(obraConverter.paraObraDTO(obraModel)).thenReturn(obraDTO);

        ObraDTO resultado = obraService.atualizarObra("111", obraDTO);

        assertNotNull(resultado);
        assertEquals("Obra Atualizada", resultado.getNome());
        verify(obraRepository, times(1)).save(obraModel);
    }

    @Test
    void deveBuscarPorId() {
        when(obraRepository.existsById("111")).thenReturn(true);
        when(obraRepository.findById("111")).thenReturn(Optional.of(obraModel));
        when(obraConverter.paraObraDTO(obraModel)).thenReturn(obraDTO);

        ObraDTO resultado = obraService.buscarPorId("111");

        assertNotNull(resultado);
        assertEquals("Obra Teste", resultado.getNome());
    }

    @Test
    void deveListarTodas() {
        List<ObraModel> listaModel = List.of(obraModel);
        List<ObraDTO> listaDTO = List.of(obraDTO);

        when(obraRepository.findAll()).thenReturn(listaModel);
        when(obraConverter.paraListaObraDTO(listaModel)).thenReturn(listaDTO);

        List<ObraDTO> resultado = obraService.listarTodas();

        assertEquals(1, resultado.size());
        assertEquals("Obra Teste", resultado.get(0).getNome());
    }

    @Test
    void deveDeletaObra() {
        when(obraRepository.existsById("111")).thenReturn(true);
        when(obraRepository.findById("111")).thenReturn(Optional.of(obraModel));
        when(autorRepository.existsById("111")).thenReturn(true);
        when(autorRepository.findById("111")).thenReturn(Optional.of(autorModel));

        obraService.deletaObra("111");

        verify(obraRepository, times(1)).delete(obraModel);
    }
}