package org.nathan.primeiracasabackend.Repository;


import org.nathan.primeiracasabackend.Model.ItemCasa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



@Repository
public interface ItemCasaRepository extends JpaRepository<ItemCasa, UUID>,
        JpaSpecificationExecutor<ItemCasa> {

    // busca itens apenas se o usuário estiver logado
    List<ItemCasa> findByUsuarioId(UUID usuarioId);

    // so pega os itens que pertençam ao usuário
    Optional<ItemCasa> findByIdAndUsuarioId(UUID id, UUID usuarioId);

}
