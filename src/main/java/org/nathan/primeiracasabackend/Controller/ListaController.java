package org.nathan.primeiracasabackend.Controller;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import org.nathan.primeiracasabackend.Service.ListaService;
import org.nathan.primeiracasabackend.dto.request.ListaRequestDTO;
import org.nathan.primeiracasabackend.dto.response.ListaResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/listas")
@RequiredArgsConstructor
public class ListaController {

    private final ListaService listaService;

    @GetMapping("")
    public ResponseEntity<List<ListaResponseDTO>> getLista(){
        return ResponseEntity.ok().body(listaService.getListaList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListaResponseDTO> getListaById(@PathVariable UUID id){
        return ResponseEntity.ok(listaService.getLista(id));
    }

    @PostMapping("")
    public ResponseEntity<ListaResponseDTO> insertLista(@RequestBody @Valid ListaRequestDTO listaRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(listaService.insertLista(listaRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListaResponseDTO> updateLista(@PathVariable UUID id, @RequestBody @Valid ListaRequestDTO listaRequestDTO){
        return ResponseEntity.ok(listaService.updateLista(id, listaRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLista(@PathVariable UUID id){
        listaService.deleteLista(id);
        return ResponseEntity.noContent().build();
    }



}