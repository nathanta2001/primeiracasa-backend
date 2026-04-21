package org.nathan.primeiracasabackend.Service;

import org.springframework.security.access.AccessDeniedException;

import java.util.List;
import java.util.UUID;
import jakarta.transaction.Transactional;
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

        Lista lista = listaRepository.findByIdAndUsuarioId(produtoDto.getIdLista(), usuario.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada ou acesso negado"));

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

        Produto produto = produtoRepository.findByIdAndUsuarioId(id, usuario.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado ou acesso negado"));

        Lista lista = listaRepository.findByIdAndUsuarioId(produtoDto.getIdLista(), usuario.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Lista de destino não encontrada"));

        produto.setNome(produtoDto.getNome());
        produto.setCategoria(produtoDto.getCategoria());
        produto.setStatus(produtoDto.getStatus());
        produto.setLista(lista);
        produto.setFotoBase64(produtoDto.getFotoBase64());

        return converteParaResponse(produtoRepository.save(produto));
    }

    @Transactional
    public void deleteProduto(UUID id) {
        Usuario usuario = authService.getUsuarioLogado();
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        if(!produto.getUsuario().getId().equals(usuario.getId())){
            throw new AccessDeniedException("Você não tem permissão pra excluir esse produto");
        }
        produtoRepository.delete(produto);
    }

    private ProdutoResponseDTO converteParaResponse(Produto produto) {
        return ProdutoResponseDTO.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .categoria(produto.getCategoria())
                .status(produto.getStatus())
                .idLista(produto.getLista().getId())
                .fotoBase64(produto.getFotoBase64())
                .build();
    }
}