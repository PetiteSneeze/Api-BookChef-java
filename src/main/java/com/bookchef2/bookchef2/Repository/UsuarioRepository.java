package com.bookchef2.bookchef2.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bookchef2.bookchef2.Models.Usuarios;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuarios, Integer> {
    Optional<Usuarios> findByEmail(String email);
}
