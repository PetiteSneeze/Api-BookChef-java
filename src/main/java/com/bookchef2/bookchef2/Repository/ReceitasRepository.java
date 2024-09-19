package com.bookchef2.bookchef2.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bookchef2.bookchef2.Models.Receitas;

public interface ReceitasRepository extends JpaRepository<Receitas, Long> {

    // Busca receitas por tipo usando o relacionamento ManyToOne com a entidade Tipo
    List<Receitas> findByTipo_Id(Long tipoId);

    // Busca uma receita por ID (opcional, já existe no JpaRepository)
    Receitas findById(long id);  // Ou você pode usar diretamente JpaRepository's findById que retorna Optional<Receitas>
    List<Receitas> findByUsuario_Id(Long usuarioId);
}
