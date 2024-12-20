package com.bookchef2.bookchef2.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bookchef2.bookchef2.Models.Receitas;
import com.bookchef2.bookchef2.Repository.ReceitasRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/receitas")
public class ReceitasController {

    @Autowired
    private ReceitasRepository repository;

      @GetMapping("/todas")
      public List<Receitas> getTodasReceitas() {
          return repository.findAll();
      }
  
      @GetMapping("/usuario/{usuarioId}")
      public List<Receitas> getReceitasPorUsuario(@PathVariable Long usuarioId) {
          return repository.findByUsuario_Id(usuarioId);
      }

    @PostMapping
    public Receitas postInserir(@RequestBody Receitas entity) {
        repository.save(entity);
        return entity;
    }

    @PutMapping
    public Receitas putAlterar(@RequestBody Receitas entity) {
        repository.save(entity);
        return entity;
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable int id) {
        if (repository.existsById((long) id)) {
            repository.deleteById((long) id);
        } else {
            throw new RuntimeException("Receita não encontrada");
        }
    }

    @PutMapping("/{id}")
    public Receitas atualizarReceita(@PathVariable Long id, @RequestBody Receitas receitaAtualizada) {
        return repository.findById(id)
                .map(receita -> {
                    receita.setNome(receitaAtualizada.getNome());
                    receita.setDescricao(receitaAtualizada.getDescricao());
                    receita.setImagemUrl(receitaAtualizada.getImagemUrl());
                    
                    return repository.save(receita);
                })
                .orElseThrow(() -> new RuntimeException("Receita não encontrada"));
    }

    @GetMapping("/detalhes/{id}")
    public Receitas getDetalhesPorId(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receita não encontrada"));
    }

    @GetMapping("/tipo/{tipoReceitaId}")
    public List<Receitas> getReceitasPorTipo(@PathVariable Long tipoReceitaId) {
        return repository.findByTipo_Id(tipoReceitaId);
    }
}
