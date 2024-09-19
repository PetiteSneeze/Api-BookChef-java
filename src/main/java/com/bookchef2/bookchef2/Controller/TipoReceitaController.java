package com.bookchef2.bookchef2.Controller;

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
import com.bookchef2.bookchef2.Models.TipoReceitas;
import com.bookchef2.bookchef2.Repository.TipoReceitasRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/tipoReceitas")
public class TipoReceitaController {

    @Autowired
    private TipoReceitasRepository repository;

    // Criar um novo tipo de receita
    @PostMapping
    public TipoReceitas criarTipoReceita(@RequestBody TipoReceitas tipoReceitas) {
        return repository.save(tipoReceitas);
    }

    // Atualizar um tipo de receita existente
    @PutMapping
    public TipoReceitas putAlterar(@RequestBody TipoReceitas entity) {
        repository.save(entity);
        return entity;
    }

    // Excluir um tipo de receita pelo ID
    // Excluir uma receita pelo ID
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable int id) {
        if (repository.existsById((long) id)) {
            repository.deleteById((long) id);
        } else {
            throw new RuntimeException("Receita não encontrada");
        }
    }

    @GetMapping("/{id}")
    public TipoReceitas buscarRceitaPorId(@PathVariable Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Receita não encontrada"));
    }

    // Selecionar todos os tipos de receita
    @GetMapping
    public Iterable<TipoReceitas> getSelecionar() {
        return repository.findAll();
    }
}