package com.app.clubnautico.repositories;

import com.app.clubnautico.domain.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatronRepository extends JpaRepository<Patron,Integer> {

    @Query("SELECT p FROM Patron p WHERE p.documentoIdentidad = :documentoIdentidad")
    public Optional<Patron> obtenerPatronByDocumentoIdentidad(String documentoIdentidad);

    public Optional<Patron> findByDocumentoIdentidad(String documentoIdentidad);
}
