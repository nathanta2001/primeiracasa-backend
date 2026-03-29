package org.nathan.primeiracasabackend.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import jakarta.transaction.Transactional;
import org.nathan.primeiracasabackend.Enums.EnumsItemCasa.ComodoItem;
import org.nathan.primeiracasabackend.Enums.EnumsItemCasa.NecessidadeItem;
import org.nathan.primeiracasabackend.Enums.EnumsItemCasa.TipoItem;
import org.nathan.primeiracasabackend.Exception.ResourceNotFoundException;
import org.nathan.primeiracasabackend.Repository.ItemCasaRepository;
import org.nathan.primeiracasabackend.Specification.ItemCasaSpecification;
import org.nathan.primeiracasabackend.dto.request.ItemCasaRequestDTO;
import org.nathan.primeiracasabackend.dto.response.ItemCasaResponseDTO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.nathan.primeiracasabackend.Model.ItemCasa;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemCasaService {

    private final ItemCasaRepository itemCasaRepository;

    public ItemCasaResponseDTO getItemCasa(UUID id){
        ItemCasa itemCasa = itemCasaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item inexistente"));

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
                .orElseThrow(() -> new ResourceNotFoundException("Item inexistente"));
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
            throw new ResourceNotFoundException("Nenhum Item encontrado com esse ID: "+ id);
        }

        itemCasaRepository.deleteById(id);
    }

    @Transactional
    public ItemCasaResponseDTO salvarFoto(UUID id, String fotoBase64){
        ItemCasa itemCasa = itemCasaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto inexistente"));

        if(fotoBase64 != null && fotoBase64.startsWith("data:image")){
            itemCasa.setFotoBase64(fotoBase64);
            itemCasaRepository.save(itemCasa);
        } else{
            throw new IllegalArgumentException("Formato de imagem invalido");
        }
        return converteParaResponse(itemCasaRepository.save(itemCasa));
    }

    private ItemCasaResponseDTO converteParaResponse(ItemCasa itemCasa){
        return ItemCasaResponseDTO.builder()
                .id(itemCasa.getId())
                .nome(itemCasa.getNome())
                .preco(itemCasa.getPreco())
                .tipo(itemCasa.getTipo())
                .necessidade(itemCasa.getNecessidade())
                .comodo(itemCasa.getComodo())
                .fotoBase64(itemCasa.getFotoBase64())
                .build();
    }


    public List<ItemCasaResponseDTO> filtrar(
            String nome,
            ComodoItem comodo,
            TipoItem tipo,
            NecessidadeItem necessidade,
            BigDecimal precoMin,
            BigDecimal precoMax) {

        Specification<ItemCasa> spec = Specification
                .where(ItemCasaSpecification.porNome(nome))
                .and(ItemCasaSpecification.porComodo(comodo))
                .and(ItemCasaSpecification.porTipo(tipo))
                .and(ItemCasaSpecification.porNecessidade(necessidade))
                .and(ItemCasaSpecification.porPrecoMinimo(precoMin))
                .and(ItemCasaSpecification.porPrecoMaximo(precoMax));

        return itemCasaRepository.findAll(spec)
                .stream()
                .map(this::converteParaResponse)
                .toList();
    }
}