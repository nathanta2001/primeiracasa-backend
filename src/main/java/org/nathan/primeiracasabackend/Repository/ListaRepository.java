package org.nathan.primeiracasabackend.Repository;


import org.nathan.primeiracasabackend.Model.Lista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



@Repository
public interface ListaRepository extends JpaRepository<Lista, UUID> {
    List<Lista> findByUsuarioId(UUID usuarioId);
    Optional<Lista> findByIdAndUsuarioId(UUID id, UUID usuarioId);
}
