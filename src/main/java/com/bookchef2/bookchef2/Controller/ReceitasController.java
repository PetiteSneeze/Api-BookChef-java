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

      // Buscar todas as receitas, incluindo as de outros usuários
      @GetMapping("/todas")
      public List<Receitas> getTodasReceitas() {
          return repository.findAll();
      }
  
      // Buscar receitas por usuário
      @GetMapping("/usuario/{usuarioId}")
      public List<Receitas> getReceitasPorUsuario(@PathVariable Long usuarioId) {
          return repository.findByUsuario_Id(usuarioId);
      }

    // Inserir uma nova receita
    @PostMapping
    public Receitas postInserir(@RequestBody Receitas entity) {
        repository.save(entity);
        return entity;
    }

    // Alterar uma receita existente
    @PutMapping
    public Receitas putAlterar(@RequestBody Receitas entity) {
        repository.save(entity);
        return entity;
    }

    // Excluir uma receita pelo ID
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable int id) {
        if (repository.existsById((long) id)) {
            repository.deleteById((long) id);
        } else {
            throw new RuntimeException("Receita não encontrada");
        }
    }

    // Atualizar uma receita específica pelo ID
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
    public Receitas getDetalhesPorId(@PathVariable Long id) { // Aqui o tipo já é Long, sem necessidade de cast
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receita não encontrada"));
    }
}
