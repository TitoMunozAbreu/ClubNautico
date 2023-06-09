package com.app.clubnautico.security.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    //metodo para buscar un usuario por email
    Optional<User> findByEmail(String email);
}
