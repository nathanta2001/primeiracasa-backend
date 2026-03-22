package org.nathan.primeiracasabackend.Controller;

import java.util.List;
import java.util.UUID;

import org.nathan.primeiracasabackend.dto.request.ItemCasaRequestDTO;
import org.nathan.primeiracasabackend.dto.response.ItemCasaResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.nathan.primeiracasabackend.Service.ItemCasaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/itens")
@RequiredArgsConstructor
public class ItemCasaController {

    private final ItemCasaService itemCasaService;

    @GetMapping("")
    public ResponseEntity<List<ItemCasaResponseDTO>> getItemCasa(){
        return ResponseEntity.ok().body(itemCasaService.getItemCasasList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemCasaResponseDTO> getItemCasaById(@PathVariable UUID id){
        return ResponseEntity.ok(itemCasaService.getItemCasa(id));
    }

    @PostMapping("")
    public ResponseEntity<ItemCasaResponseDTO> insertItemCasa(@RequestBody ItemCasaRequestDTO itemCasaRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(itemCasaService.insertItemCasa(itemCasaRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemCasaResponseDTO> updateItemCasa(@PathVariable UUID id, @RequestBody ItemCasaRequestDTO itemCasaRequestDTO){
        return ResponseEntity.ok(itemCasaService.updateItemCasa(id, itemCasaRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItemCasa(@PathVariable UUID id){
        itemCasaService.deleteItemCasa(id);
        return ResponseEntity.noContent().build();
    }
}