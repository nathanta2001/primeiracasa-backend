package org.nathan.primeiracasabackend.Repository;


import org.nathan.primeiracasabackend.Model.ItemCasa;
import org.nathan.primeiracasabackend.Model.Lista;
import org.nathan.primeiracasabackend.Model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;



@Repository
public interface ListaRepository extends JpaRepository<Lista, UUID> {

    List<Produto> findByLista(Lista lista);
    List<Lista> findByNomeContaining(String nome);

    boolean existsByNomeContaining(String nome);
}
