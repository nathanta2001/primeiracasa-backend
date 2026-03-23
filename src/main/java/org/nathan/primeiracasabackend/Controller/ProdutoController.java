package org.nathan.primeiracasabackend.Controller;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import org.nathan.primeiracasabackend.Service.ProdutoService;
import org.nathan.primeiracasabackend.dto.request.ProdutoRequestDTO;
import org.nathan.primeiracasabackend.dto.response.ProdutoResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping("")
    public ResponseEntity<List<ProdutoResponseDTO>> getProduto(){
        return ResponseEntity.ok().body(produtoService.getProdutoList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> getProdutoById(@PathVariable UUID id){
        return ResponseEntity.ok(produtoService.getProduto(id));
    }

    @PostMapping("")
    public ResponseEntity<ProdutoResponseDTO> insertProduto(@RequestBody @Valid ProdutoRequestDTO produtoRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.insertProduto(produtoRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> updateProduto(@PathVariable("id") UUID id, @RequestBody @Valid ProdutoRequestDTO produtoRequestDTO){
        return ResponseEntity.ok(produtoService.updateProduto(id, produtoRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable UUID id){
        produtoService.deleteProduto(id);
        return ResponseEntity.noContent().build();
    }
}