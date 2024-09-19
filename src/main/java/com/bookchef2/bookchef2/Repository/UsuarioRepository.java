package com.bookchef2.bookchef2.Repository;
import com.bookchef2.bookchef2.Models.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Integer> {
    // Definindo o método findByEmail
    Optional<Usuarios> findByEmail(String email);
}