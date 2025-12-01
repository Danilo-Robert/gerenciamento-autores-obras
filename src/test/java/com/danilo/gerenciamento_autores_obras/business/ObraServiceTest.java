//package com.danilo.gerenciamento_autores_obras.business;
//
//import com.danilo.gerenciamento_autores_obras.business.dto.AutorDTO;
//import com.danilo.gerenciamento_autores_obras.business.dto.ObraDTO;
//import com.danilo.gerenciamento_autores_obras.business.mapper.ObraConverter;
//import com.danilo.gerenciamento_autores_obras.infrastructure.model.AutorModel;
//import com.danilo.gerenciamento_autores_obras.infrastructure.model.ObraModel;
//import com.danilo.gerenciamento_autores_obras.infrastructure.repository.AutorRepository;
//import com.danilo.gerenciamento_autores_obras.infrastructure.repository.ObraRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class ObraServiceTest {
//
//    @Mock
//    private ObraRepository obraRepository;
//
//    @Mock
//    private AutorRepository autorRepository;
//
//    @Mock
//    private ObraConverter obraConverter;
//
//    private ObraDTO obraDTO;
//    private ObraModel obraModel;
//
//    private AutorModel autorModel;
//
//    @Autowired
//    @InjectMocks
//    private ObraService obraService;
//
//    @BeforeEach
//    void setup(){
//        MockitoAnnotations.openMocks(this);
//
//        autorModel = AutorModel.builder()
//                .id("12345")
//                .nome("Autor Teste")
//                .email("autor@test.com")
//                .dataNascimento(LocalDate.of(1990, 1, 1))
//                .paisOrigem("Brasil")
//                .sexo("M")
//                .cpf("12345678999")
//                .obrasIds(new ArrayList<>())
//                .build();
//
//        obraDTO = ObraDTO.builder()
//                .nome("Obra Teste")
//                .descricao("Descrição teste da obra")
//                .dataPublicacao(LocalDate.of(2020, 5, 10))
//                .dataExposicao(LocalDate.of(2021, 1, 20))
//                .autoresId(List.of("111"))
//                .build();
//
//        obraModel = ObraModel.builder()
//                .id("12345")
//                .nome("Obra Teste")
//                .descricao("Descrição teste da obra")
//                .dataPublicacao(LocalDate.of(2020, 5, 10))
//                .dataExposicao(LocalDate.of(2021, 1, 20))
//                .autoresIds(new ArrayList<>(List.of("111")))
//                .build();
//    }
//    @Test
//    void deveCriarObra() {
//        when(autorRepository.findById("12345")).thenReturn(Optional.of(autorModel));
//        when(obraConverter.paraObraModel(obraDTO)).thenReturn(obraModel);
//        when(obraRepository.save(obraModel)).thenReturn(obraModel);
//        when(obraConverter.paraObraDTO(obraModel)).thenReturn(obraDTO);
//
//        ObraDTO resultado = obraService.criarObra(obraDTO);
//
//        assertNotNull(resultado);
//        assertEquals("Obra Teste", resultado.getNome());
//        verify(obraRepository, times(1)).save(obraModel);
//    }
//
//    @Test
//    void deveValidarAutores() {
//        when(autorRepository.findById("12345")).thenReturn(Optional.of(autorModel));
//
//        List<AutorModel> autores = obraService.validarAutores(List.of("111"));
//
//        assertNotNull(autores);
//        assertEquals(1, autores.size());
//        assertEquals("Autor Teste", autores.get(0).getNome());
//        verify(autorRepository, times(1)).findById("12345");
//    }
//
//    @Test
//    void deveAtualizarObrasDosAutores() {
//        List<AutorModel> autores = List.of(autorModel);
//
//        assertDoesNotThrow(() -> obraService.atualizarObrasDosAutores(obraModel, autores));
//    }
//
//    @Test
//    void deveAtualizarObra() {
//        when(obraRepository.findById("12345")).thenReturn(Optional.of(obraModel));
//        when(autorRepository.findById("12345")).thenReturn(Optional.of(autorModel));
//        when(obraConverter.paraObraModel(obraDTO)).thenReturn(obraModel);
//        when(obraRepository.save(obraModel)).thenReturn(obraModel);
//        when(obraConverter.paraObraDTO(obraModel)).thenReturn(obraDTO);
//
//        ObraDTO resultado = obraService.atualizarObra("12345", obraDTO);
//
//        assertNotNull(resultado);
//        assertEquals("Obra Atualizada", resultado.getNome());
//        verify(obraRepository, times(1)).save(obraModel);
//    }
//
//    @Test
//    void buscarPorId() {
//        when();
//    }
//
//    @Test
//    void listarTodas() {
//    }
//
//    @Test
//    void deletaObra() {
//    }
//}