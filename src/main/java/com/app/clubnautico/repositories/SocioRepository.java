package com.app.clubnautico.repositories;


import com.app.clubnautico.domain.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocioRepository extends JpaRepository<Socio,Integer> {

}
