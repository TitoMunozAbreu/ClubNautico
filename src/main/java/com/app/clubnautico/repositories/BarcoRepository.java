package com.app.clubnautico.repositories;

import com.app.clubnautico.domain.Barco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface BarcoRepository extends JpaRepository<Barco,Integer> {
    @Query("SELECT b FROM Barco b WHERE b.numeroMatricula = :numeroMatricula")
    Optional<Barco> findBarcoByNumeroMatricula(String numeroMatricula);

    @Query("SELECT b FROM Barco b WHERE b.socio.socioId = :socioID")
    Set<Barco> listarBarcosBySocioID(Integer socioID);

    @Query("SELECT b FROM Barco b " +
            "WHERE b.barcoID = :barcoID")
    Optional<Barco> obtenerBarcoBySocioID(Integer barcoID);
}
