package org.nathan.primeiracasabackend.Service;


import jakarta.transaction.Transactional;
import org.nathan.primeiracasabackend.Exception.ResourceNotFoundException;
import org.nathan.primeiracasabackend.Model.ItemCasa;
import org.nathan.primeiracasabackend.Repository.ListaRepository;
import org.nathan.primeiracasabackend.dto.request.ItemCasaRequestDTO;
import org.nathan.primeiracasabackend.dto.request.ListaRequestDTO;
import org.nathan.primeiracasabackend.dto.response.ItemCasaResponseDTO;
import org.nathan.primeiracasabackend.dto.response.ListaResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.nathan.primeiracasabackend.Model.Lista;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListaService {

    private final ListaRepository listaRepository;

    public ListaResponseDTO getLista(UUID id) {

        Lista lista = listaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lista inexistente"));

        return converteParaResponse(lista);
    }

    public List<ListaResponseDTO> getListaList() {
        return listaRepository.findAll()
                .stream()
                .map(this::converteParaResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ListaResponseDTO insertLista(ListaRequestDTO listaDto){
        Lista lista = Lista.builder()
                .nome(listaDto.getNome())
                .build();

        Lista salvo = listaRepository.save(lista);
        return converteParaResponse(salvo);
    }

    @Transactional
    public ListaResponseDTO updateLista(UUID id, ListaRequestDTO ListaDto) {
        Lista lista = listaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lista inexistente"));
        lista.setNome(ListaDto.getNome());
        return converteParaResponse(listaRepository.save(lista));
    }

    @Transactional
    public void deleteLista(UUID id){
        if(!listaRepository.existsById(id)){
            throw new ResourceNotFoundException("Nenhuma lista encontrada com esse ID: "+ id);
        }

        listaRepository.deleteById(id);
    }

    private ListaResponseDTO converteParaResponse(Lista lista){
        return ListaResponseDTO.builder()
                .id(lista.getId())
                .nome(lista.getNome())
                .build();
    }

}