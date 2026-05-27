package org.nathan.primeiracasabackend.Service;

import org.springframework.security.access.AccessDeniedException;

import java.util.List;
import java.util.UUID;
import org.nathan.primeiracasabackend.Exception.ResourceNotFoundException;
import org.nathan.primeiracasabackend.Model.Lista;
import org.nathan.primeiracasabackend.Model.Usuario;
import org.nathan.primeiracasabackend.Repository.ListaRepository;
import org.nathan.primeiracasabackend.Repository.ProdutoRepository;
import org.nathan.primeiracasabackend.dto.request.ProdutoRequestDTO;
import org.nathan.primeiracasabackend.dto.response.ProdutoResponseDTO;
import org.springframework.stereotype.Service;
import org.nathan.primeiracasabackend.Model.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ListaRepository listaRepository;
    private final AuthService authService;


    public List<ProdutoResponseDTO> getProdutoList() {
        return produtoRepository.findByUsuarioId(authService.getUsuarioLogado().getId())
                .stream()
                .map(this::converteParaResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> getProdutosPorLista(UUID listaId) {
        Usuario usuario = authService.getUsuarioLogado();
        Lista lista = listaRepository.findById(listaId)
                .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada"));

        // é o dono ou colaborador da lista?
        boolean temAcesso = lista.getUsuario().getId().equals(usuario.getId()) ||
                lista.getColaboradores().stream().anyMatch(c -> c.getId().equals(usuario.getId()));

        if (!temAcesso) {
            throw new AccessDeniedException("Você não tem permissão para ver os produtos desta lista.");
        }

        return produtoRepository.findByListaId(listaId)
                .stream()
                .map(this::converteParaResponse)
                .toList();
    }

    public ProdutoResponseDTO getProduto(UUID id) {
        Usuario usuario = authService.getUsuarioLogado();
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        if(!produto.getUsuario().getId().equals(usuario.getId())){
            throw new AccessDeniedException("Você não tem permissão pra acessar esse produto");
        }

        return converteParaResponse(produto);
    }

    @Transactional
    public ProdutoResponseDTO insertProduto(ProdutoRequestDTO produtoDto) {
        Usuario usuario = authService.getUsuarioLogado();

        Lista lista = listaRepository.findById(produtoDto.getIdLista())
                .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada ou acesso negado"));

        // Validação: Usuário é o dono OU é colaborador da lista?
        boolean temAcesso = lista.getUsuario().getId().equals(usuario.getId()) ||
                lista.getColaboradores().stream().anyMatch(c -> c.getId().equals(usuario.getId()));

        if (!temAcesso) {
            throw new AccessDeniedException("Você não tem permissão para adicionar produtos a esta lista.");
        }

        Produto produto = Produto.builder()
                .nome(produtoDto.getNome())
                .categoria(produtoDto.getCategoria())
                .status(produtoDto.getStatus())
                .lista(lista)
                .fotoBase64(produtoDto.getFotoBase64())
                .usuario(usuario)
                .build();

        return converteParaResponse(produtoRepository.save(produto));
    }

    @Transactional
    public ProdutoResponseDTO updateProduto(UUID id, ProdutoRequestDTO produtoDto) {

        Usuario usuario = authService.getUsuarioLogado();
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Produto não encontrado"));

        Lista lista = produto.getLista();

        // Usuário é dono ou colaborador?
        boolean temAcesso = lista.getUsuario().getId().equals(usuario.getId()) ||
                        lista.getColaboradores().stream()
                                .anyMatch(c -> c.getId().equals(usuario.getId()));

        if (!temAcesso) {
            throw new AccessDeniedException(
                    "Você não tem permissão para editar este produto.");
        }

        produto.setNome(produtoDto.getNome());
        produto.setCategoria(produtoDto.getCategoria());
        produto.setStatus(produtoDto.getStatus());
        produto.setFotoBase64(produtoDto.getFotoBase64());

        return converteParaResponse(produtoRepository.save(produto));
    }

    @Transactional
    public void deleteProduto(UUID id) {

        Usuario usuario = authService.getUsuarioLogado();
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        Lista lista = produto.getLista();
        boolean temAcesso = lista.getUsuario().getId().equals(usuario.getId()) ||
                lista.getColaboradores().stream()
                        .anyMatch(c -> c.getId().equals(usuario.getId()));

        if (!temAcesso) {
            throw new AccessDeniedException(
                    "Você não tem permissão para acessar esta lista.");
        }

        produtoRepository.delete(produto);
    }

    private ProdutoResponseDTO converteParaResponse(Produto produto) {
        return ProdutoResponseDTO.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .categoria(produto.getCategoria())
                .status(produto.getStatus())
                .idLista(produto.getLista() != null ? produto.getLista().getId() : null)
                .fotoBase64(produto.getFotoBase64())
                .build();
    }
}