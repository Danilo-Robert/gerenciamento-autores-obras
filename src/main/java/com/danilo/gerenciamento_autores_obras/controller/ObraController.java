package com.danilo.gerenciamento_autores_obras.controller;

import com.danilo.gerenciamento_autores_obras.business.ObraService;
import com.danilo.gerenciamento_autores_obras.business.dto.ObraDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/obras")
public class ObraController {

    private final ObraService obraService;

    @PostMapping
    public ResponseEntity<ObraDTO> criarObra(@Valid @RequestBody ObraDTO dto){
        return ResponseEntity.ok(obraService.criarObra(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObraDTO> atualizarObra(@PathVariable String id,
                                                 @Valid @RequestBody ObraDTO dto){
        return ResponseEntity.ok(obraService.atualizarObra(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObraDTO> buscarPorId(@PathVariable String id){
        return ResponseEntity.ok(obraService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ObraDTO>> listarTodas(){
        return ResponseEntity.ok(obraService.listarTodas());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ObraDTO> deletarObra(@PathVariable String id){
        obraService.deletaObra(id);
        return ResponseEntity.ok().build();
    }
}
