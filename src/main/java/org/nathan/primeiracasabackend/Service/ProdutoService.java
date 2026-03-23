package org.nathan.primeiracasabackend.Service;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import jakarta.transaction.Transactional;
import org.nathan.primeiracasabackend.Exception.ResourceNotFoundException;
import org.nathan.primeiracasabackend.Model.Lista;
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

    public ProdutoResponseDTO getProduto(UUID id){
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException( "Produto inexistente"));

        return converteParaResponse(produto);
    }

    public List<ProdutoResponseDTO> getProdutoList(){
        return produtoRepository.findAll()
                .stream()
                .map(this::converteParaResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProdutoResponseDTO insertProduto(ProdutoRequestDTO produtoDto){
        Lista lista = listaRepository.findById(produtoDto.getIdLista())
                .orElseThrow(() -> new ResourceNotFoundException("Lista inexistente"));

        Produto produto = Produto.builder()
                .nome(produtoDto.getNome())
                .categoria(produtoDto.getCategoria())
                .status(produtoDto.getStatus())
                .lista(lista)
                .build();

        Produto salvo = produtoRepository.save(produto);
        return converteParaResponse(salvo);
    }

    @Transactional
    public ProdutoResponseDTO updateProduto(UUID id, ProdutoRequestDTO produtoDto) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto inexistente"));

        Lista lista = listaRepository.findById(produtoDto.getIdLista())
                .orElseThrow(() -> new ResourceNotFoundException("Lista inexistente"));

        produto.setNome(produtoDto.getNome());
        produto.setCategoria(produtoDto.getCategoria());
        produto.setStatus(produtoDto.getStatus());
        produto.setLista(lista);
        return converteParaResponse(produtoRepository.save(produto));
    }

    @Transactional
    public void deleteProduto(UUID id){
        if(!produtoRepository.existsById(id)){
            throw new ResourceNotFoundException("Nenhum Produto encontrado com esse ID: "+ id);
        }

        produtoRepository.deleteById(id);
    }

    private ProdutoResponseDTO converteParaResponse(Produto produto){
        return ProdutoResponseDTO.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .categoria(produto.getCategoria())
                .status(produto.getStatus())
                .build();
    }

}