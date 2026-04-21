package org.nathan.primeiracasabackend.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import jakarta.transaction.Transactional;
import org.nathan.primeiracasabackend.Enums.EnumsItemCasa.ComodoItem;
import org.nathan.primeiracasabackend.Enums.EnumsItemCasa.NecessidadeItem;
import org.nathan.primeiracasabackend.Enums.EnumsItemCasa.TipoItem;
import org.nathan.primeiracasabackend.Exception.ResourceNotFoundException;
import org.nathan.primeiracasabackend.Model.Usuario;
import org.nathan.primeiracasabackend.Repository.ItemCasaRepository;
import org.nathan.primeiracasabackend.Specification.ItemCasaSpecification;
import org.nathan.primeiracasabackend.dto.request.ItemCasaRequestDTO;
import org.nathan.primeiracasabackend.dto.response.ItemCasaResponseDTO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.nathan.primeiracasabackend.Model.ItemCasa;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemCasaService {

    private final ItemCasaRepository itemCasaRepository;
    private final AuthService authService;


    public List<ItemCasaResponseDTO> getItemCasasList() {
        Usuario usuario = authService.getUsuarioLogado();
        return itemCasaRepository.findByUsuarioId(usuario.getId()).stream()
                .map(this::converteParaResponse)
                .toList();
    }

    public ItemCasaResponseDTO getItemCasa(UUID id) {
        Usuario usuario = authService.getUsuarioLogado();

        ItemCasa item = itemCasaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado"));

        if (!item.getUsuario().getId().equals(usuario.getId())) {
            throw new AccessDeniedException("Você não tem permissão para acessar este item");
        }

        return converteParaResponse(item);
    }

    @Transactional
    public ItemCasaResponseDTO insertItemCasa(ItemCasaRequestDTO itemCasaDto) {
        ItemCasa itemCasa = ItemCasa.builder()
                .nome(itemCasaDto.getNome())
                .preco(itemCasaDto.getPreco())
                .tipo(itemCasaDto.getTipo())
                .necessidade(itemCasaDto.getNecessidade())
                .comodo(itemCasaDto.getComodo())
                .fotoBase64(itemCasaDto.getFotoBase64())
                .usuario(authService.getUsuarioLogado())
                .build();

        return converteParaResponse(itemCasaRepository.save(itemCasa));
    }

    @Transactional
    public ItemCasaResponseDTO updateItemCasa(UUID id, ItemCasaRequestDTO dto) {
        Usuario usuario = authService.getUsuarioLogado();

        ItemCasa item = itemCasaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado"));

        if (!item.getUsuario().getId().equals(usuario.getId())) {
            throw new AccessDeniedException("Você não tem permissão para editar este item");
        }

        item.setNome(dto.getNome());
        item.setPreco(dto.getPreco());
        item.setTipo(dto.getTipo());
        item.setNecessidade(dto.getNecessidade());
        item.setComodo(dto.getComodo());
        item.setFotoBase64(dto.getFotoBase64());

        return converteParaResponse(itemCasaRepository.save(item));
    }

    @Transactional
    public void deleteItemCasa(UUID id) {
        Usuario usuario = authService.getUsuarioLogado();
        ItemCasa item = itemCasaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado"));

        if (!item.getUsuario().getId().equals(usuario.getId())) {
            throw new AccessDeniedException("Você não tem permissão para excluir este item");
        }

        itemCasaRepository.delete(item);
    }

    public List<ItemCasaResponseDTO> filtrar(
            String nome, ComodoItem comodo, TipoItem tipo,
            NecessidadeItem necessidade, BigDecimal precoMin, BigDecimal precoMax) {

        Usuario usuario = authService.getUsuarioLogado();
        Specification<ItemCasa> spec = Specification
                .where(ItemCasaSpecification.pertenceAoUsuario(usuario.getId()))
                .and(ItemCasaSpecification.porNome(nome))
                .and(ItemCasaSpecification.porComodo(comodo))
                .and(ItemCasaSpecification.porTipo(tipo))
                .and(ItemCasaSpecification.porNecessidade(necessidade))
                .and(ItemCasaSpecification.porPrecoMinimo(precoMin))
                .and(ItemCasaSpecification.porPrecoMaximo(precoMax));

        return itemCasaRepository.findAll(spec).stream()
                .map(this::converteParaResponse)
                .toList();
    }

    private ItemCasaResponseDTO converteParaResponse(ItemCasa itemCasa) {
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
}