package com.danilo.gerenciamento_autores_obras.controller;

import com.danilo.gerenciamento_autores_obras.business.AutorService;
import com.danilo.gerenciamento_autores_obras.business.dto.AutorDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorService autorService;

    @PostMapping
    public ResponseEntity<AutorDTO> criarAutor(@Valid @RequestBody AutorDTO dto){
        return ResponseEntity.ok(autorService.criarAutor(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutorDTO> atualizarAutor(@PathVariable String id,
                                                   @Valid @RequestBody AutorDTO dto){
        return ResponseEntity.ok(autorService.atualizarAutor(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> buscarPorId(@PathVariable String id){
        return ResponseEntity.ok(autorService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> listarTodos(){
        return ResponseEntity.ok(autorService.listaTodos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAutor(@PathVariable String id){
        autorService.deletarAutor(id);
        return ResponseEntity.ok().build();
    }
}
