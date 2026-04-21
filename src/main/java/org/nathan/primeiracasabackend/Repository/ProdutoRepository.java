package org.nathan.primeiracasabackend.Repository;


import org.nathan.primeiracasabackend.Enums.EnumsProduto.CategoriaProduto;
import org.nathan.primeiracasabackend.Enums.EnumsProduto.StatusProduto;
import org.nathan.primeiracasabackend.Model.Lista;
import org.nathan.primeiracasabackend.Model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

    List<Produto> findByUsuarioId(UUID id);
    Optional<Produto> findByIdAndUsuarioId(UUID id, UUID usuarioId);
    List<Produto> findByCategoriaAndUsuarioId(CategoriaProduto categoria, UUID usuarioId);

    boolean existsByNomeContaining(String nome);
}
