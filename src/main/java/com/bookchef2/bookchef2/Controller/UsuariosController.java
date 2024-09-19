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
import com.bookchef2.bookchef2.Models.Usuarios;
import com.bookchef2.bookchef2.Repository.UsuarioRepository;

import java.util.Optional;  // Importação correta

@CrossOrigin("*")
@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired 
    //tiporeceita 
    private UsuarioRepository repository;

    @PostMapping
    public Usuarios criarUsuario(@RequestBody Usuarios usuario) {
        Optional<Usuarios> usuarioExistente = repository.findByEmail(usuario.getEmail());
        if (usuarioExistente.isPresent()) {
            throw new RuntimeException("Usuário com este e-mail já existe");
        }
        return repository.save(usuario);
    }

    @PutMapping("/{id}")
    public Usuarios atualizarUsuario(@PathVariable int id, @RequestBody Usuarios usuarioAtualizado) {
        return repository.findById(id)
            .map(usuario -> {
                Optional<Usuarios> usuarioExistente = repository.findByEmail(usuarioAtualizado.getEmail());
                // Verifica se o email já está em uso por outro usuário
                if (usuarioExistente.isPresent() && usuarioExistente.get().getId() != id) { // Aqui uso `!=` ao invés de `equals`
                    throw new RuntimeException("E-mail já está em uso por outro usuário");
                }
    
                usuario.setNome(usuarioAtualizado.getNome());
                usuario.setEmail(usuarioAtualizado.getEmail());
                usuario.setSenha(usuarioAtualizado.getSenha());
                return repository.save(usuario);
            })
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }

    @GetMapping
    public Iterable<Usuarios> getSelecionar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Usuarios buscarUsuarioPorId(@PathVariable int id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}
