package com.bookchef2.bookchef2.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bookchef2.bookchef2.Models.Receitas;

public interface ReceitasRepository extends JpaRepository<Receitas, Long> {

    List<Receitas> findByTipo_Id(Long tipoId);
    Receitas findById(long id);  
    List<Receitas> findByUsuario_Id(Long usuarioId);
}
