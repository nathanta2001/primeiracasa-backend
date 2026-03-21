package org.nathan.primeiracasabackend.Repository;

import org.nathan.primeiracasabackend.Enums.EnumsItemCasa.ComodoItem;
import org.nathan.primeiracasabackend.Enums.EnumsItemCasa.NecessidadeItem;
import org.nathan.primeiracasabackend.Enums.EnumsItemCasa.TipoItem;
import org.nathan.primeiracasabackend.Model.ItemCasa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;



@Repository
public interface ItemCasaRepository extends JpaRepository<ItemCasa, UUID> {

    List<ItemCasa> findByComodo(ComodoItem comodo);
    List<ItemCasa> findByNomeContaining(String nome);
    List<ItemCasa> findByPreco(BigDecimal preco);
    List<ItemCasa> findByNecessidade(NecessidadeItem necessidade);
    List<ItemCasa> findByTipo(TipoItem tipo);

    boolean existsByNomeContaining(String nome);
}
