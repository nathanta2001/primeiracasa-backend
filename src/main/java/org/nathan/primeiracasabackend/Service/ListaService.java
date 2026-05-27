package org.nathan.primeiracasabackend.Service;

import jakarta.transaction.Transactional;
import org.nathan.primeiracasabackend.Exception.ResourceNotFoundException;
import org.nathan.primeiracasabackend.Model.Usuario;
import org.nathan.primeiracasabackend.Repository.ListaRepository;
import org.nathan.primeiracasabackend.Repository.UsuarioRepository;
import org.nathan.primeiracasabackend.dto.request.ListaRequestDTO;
import org.nathan.primeiracasabackend.dto.response.ListaResponseDTO;

import org.springframework.stereotype.Service;
import org.nathan.primeiracasabackend.Model.Lista;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.AccessDeniedException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ListaService {

    private final ListaRepository listaRepository;
    private final AuthService authService;
    private final UsuarioRepository usuarioRepository;

    public List<ListaResponseDTO> getListaList() {
        Usuario usuario = authService.getUsuarioLogado();

        // Busca listas criadas pelo usuário
        List<Lista> criadas = listaRepository.findByUsuarioId(usuario.getId());

        List<Lista> compartilhadas = listaRepository.findByColaboradoresId(usuario.getId());

        // Mescla e converte para DTO
        return Stream.concat(criadas.stream(), compartilhadas.stream())
                .distinct()
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
    public void compartilharComUsuario(UUID idLista, String emailConvidado) {
        Usuario dono = authService.getUsuarioLogado();
        Lista lista = listaRepository.findById(idLista)
                .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada"));

        // Apenas o dono original pode compartilhar a lista com novas pessoas
        if (!lista.getUsuario().getId().equals(dono.getId())) {
            throw new AccessDeniedException("Apenas o proprietário pode compartilhar esta lista.");
        }

        if (dono.getEmail().equalsIgnoreCase(emailConvidado)) {
            throw new IllegalArgumentException("Você não pode compartilhar a lista com você mesmo.");
        }

        Usuario colaborador = usuarioRepository.findByEmail(emailConvidado)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum usuário cadastrado com o e-mail: " + emailConvidado));

        if (!lista.getColaboradores().contains(colaborador)) {
            lista.getColaboradores().add(colaborador);
            listaRepository.save(lista);
        }
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