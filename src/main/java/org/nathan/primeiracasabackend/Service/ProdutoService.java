package org.nathan.primeiracasabackend.Service;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import jakarta.transaction.Transactional;
import org.nathan.primeiracasabackend.Model.ItemCasa;
import org.nathan.primeiracasabackend.Repository.ProdutoRepository;
import org.nathan.primeiracasabackend.dto.request.ItemCasaRequestDTO;
import org.nathan.primeiracasabackend.dto.request.ProdutoRequestDTO;
import org.nathan.primeiracasabackend.dto.response.ItemCasaResponseDTO;
import org.nathan.primeiracasabackend.dto.response.ProdutoResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.nathan.primeiracasabackend.Model.Produto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoResponseDTO getProduto(UUID id){
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto inexistente"));

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
        Produto produto = Produto.builder()
                .nome(produtoDto.getNome())
                .categoria(produtoDto.getCategoria())
                .status(produtoDto.getStatus())
                .build();

        Produto salvo = produtoRepository.save(produto);
        return converteParaResponse(salvo);
    }

    @Transactional
    public ProdutoResponseDTO updateProduto(UUID id, ProdutoRequestDTO ProdutoDto) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto inexistente"));
        produto.setNome(ProdutoDto.getNome());
        produto.setCategoria(ProdutoDto.getCategoria());
        produto.setStatus(ProdutoDto.getStatus());
        return converteParaResponse(produtoRepository.save(produto));
    }

    @Transactional
    public void deleteProduto(UUID id){
        if(!produtoRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum Produto encontrado com esse ID: "+ id);
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