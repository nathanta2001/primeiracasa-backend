package org.nathan.primeiracasabackend.Repository;


import org.nathan.primeiracasabackend.Enums.EnumsProduto.CategoriaProduto;
import org.nathan.primeiracasabackend.Enums.EnumsProduto.StatusProduto;
import org.nathan.primeiracasabackend.Model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;



@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

    List<Produto> findByCategoria(CategoriaProduto categoria);
    List<Produto> findByNomeIgnoreCase(String nome);
    List<Produto> findByStatus(StatusProduto status);

    boolean existsByNomeContaining(String nome);
}
