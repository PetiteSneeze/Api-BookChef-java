package com.bookchef2.bookchef2.Repository;
import com.bookchef2.bookchef2.Models.Receitas;
import com.bookchef2.bookchef2.Models.TipoReceitas;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoReceitasRepository extends CrudRepository<TipoReceitas, Long> {
    
    @Query(value = "select * from tipoReceita where id=?1", nativeQuery = true)
    List<Receitas> findByTipoReceita_Id(Long tipoReceitaId);

}
