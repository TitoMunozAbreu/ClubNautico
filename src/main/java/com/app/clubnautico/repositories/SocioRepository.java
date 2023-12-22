package com.app.clubnautico.repositories;


import com.app.clubnautico.domain.Socio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocioRepository extends JpaRepository<Socio,Integer> {
    Page<Socio> findByNombreContaining(String nombre, Pageable pageable);
    boolean existsSocioByDocumentoIdentidad(String documentoIdentidad);

    boolean existsSocioByEmail(String email);

}
