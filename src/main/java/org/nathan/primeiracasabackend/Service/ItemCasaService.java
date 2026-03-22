package org.nathan.primeiracasabackend.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import org.nathan.primeiracasabackend.Repository.ItemCasaRepository;
import org.nathan.primeiracasabackend.dto.request.ItemCasaRequestDTO;
import org.nathan.primeiracasabackend.dto.response.ItemCasaResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.nathan.primeiracasabackend.Model.ItemCasa;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemCasaService {

    private final ItemCasaRepository itemCasaRepository;

    public ItemCasaResponseDTO getItemCasa(UUID id){
        ItemCasa itemCasa = itemCasaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item inexistente"));

        return converteParaResponse(itemCasa);
    }

    public List<ItemCasaResponseDTO> getItemCasasList(){
        return itemCasaRepository.findAll()
                .stream()
                .map(this::converteParaResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ItemCasaResponseDTO insertItemCasa(ItemCasaRequestDTO itemCasaDto){
        ItemCasa itemCasa = ItemCasa.builder()
                .nome(itemCasaDto.getNome())
                .preco(itemCasaDto.getPreco())
                .tipo(itemCasaDto.getTipo())
                .necessidade(itemCasaDto.getNecessidade())
                .comodo(itemCasaDto.getComodo())
                .build();

        ItemCasa salvo = itemCasaRepository.save(itemCasa);
        return converteParaResponse(salvo);
    }

    @Transactional
    public ItemCasaResponseDTO updateItemCasa(UUID id, ItemCasaRequestDTO dto) {
        ItemCasa itemCasa = itemCasaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item inexistente"));
        itemCasa.setNome(dto.getNome());
        itemCasa.setPreco(dto.getPreco());
        itemCasa.setTipo(dto.getTipo());
        itemCasa.setNecessidade(dto.getNecessidade());
        itemCasa.setComodo(dto.getComodo());
        return converteParaResponse(itemCasaRepository.save(itemCasa));
    }

    @Transactional
    public void deleteItemCasa(UUID id){
        if(!itemCasaRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum Item encontrado com esse ID: "+ id);
        }

        itemCasaRepository.deleteById(id);
    }

    private ItemCasaResponseDTO converteParaResponse(ItemCasa itemCasa){
        return ItemCasaResponseDTO.builder()
                .id(itemCasa.getId())
                .nome(itemCasa.getNome())
                .preco(itemCasa.getPreco())
                .tipo(itemCasa.getTipo())
                .necessidade(itemCasa.getNecessidade())
                .comodo(itemCasa.getComodo())
                .build();
    }
}