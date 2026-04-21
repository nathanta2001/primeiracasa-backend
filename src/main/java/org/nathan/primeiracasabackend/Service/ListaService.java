package org.nathan.primeiracasabackend.Service;

import jakarta.transaction.Transactional;
import org.nathan.primeiracasabackend.Exception.ResourceNotFoundException;
import org.nathan.primeiracasabackend.Model.Usuario;
import org.nathan.primeiracasabackend.Repository.ListaRepository;
import org.nathan.primeiracasabackend.dto.request.ListaRequestDTO;
import org.nathan.primeiracasabackend.dto.response.ListaResponseDTO;

import org.springframework.stereotype.Service;
import org.nathan.primeiracasabackend.Model.Lista;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.AccessDeniedException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ListaService {

    private final ListaRepository listaRepository;
    private final AuthService authService;

    public List<ListaResponseDTO> getListaList() {
        return listaRepository.findByUsuarioId(authService.getUsuarioLogado().getId())
                .stream()
                .map(this::converteParaResponse)
                .toList();
    }

    public ListaResponseDTO getLista(UUID id) {
        Lista lista = listaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada"));

        if (!lista.getUsuario().getId().equals(authService.getUsuarioLogado().getId())) {
            throw new AccessDeniedException("Acesso negado a esta lista");
        }

        return converteParaResponse(lista);
    }

    @Transactional
    public ListaResponseDTO insertLista(ListaRequestDTO listaDto) {
        Lista lista = Lista.builder()
                .nome(listaDto.getNome())
                .usuario(authService.getUsuarioLogado())
                .build();
        return converteParaResponse(listaRepository.save(lista));
    }

    @Transactional
    public ListaResponseDTO updateLista(UUID id, ListaRequestDTO listaDto) {
        Usuario usuario = authService.getUsuarioLogado();
        Lista lista = listaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada"));

        if(!lista.getUsuario().getId().equals(usuario.getId())){
            throw new AccessDeniedException("Você não tem permissão para editar essa lista");
        }

        lista.setNome(listaDto.getNome());
        return converteParaResponse(listaRepository.save(lista));
    }

    @Transactional
    public void deleteLista(UUID id) {
        Lista lista = listaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada"));

        if (!lista.getUsuario().getId().equals(authService.getUsuarioLogado().getId())) {
            throw new AccessDeniedException("Permissão negada para excluir esta lista");
        }
        listaRepository.delete(lista);
    }

    private ListaResponseDTO converteParaResponse(Lista lista) {
        return ListaResponseDTO.builder()
                .id(lista.getId())
                .nome(lista.getNome())
                .build();
    }
}