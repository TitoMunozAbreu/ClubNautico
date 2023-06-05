package com.app.clubnautico.repositories;

import com.app.clubnautico.domain.RegistroSalida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RegistroSalidaRepository extends JpaRepository<RegistroSalida,Integer> {

    @Query("SELECT s FROM RegistroSalida s WHERE s.barco.barcoID = :barcoID")
    public Set<RegistroSalida> listarSalidaSegunBarcoID(Integer barcoID);
}
