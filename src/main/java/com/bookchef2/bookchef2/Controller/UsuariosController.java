package com.bookchef2.bookchef2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bookchef2.bookchef2.Models.Usuarios;
import com.bookchef2.bookchef2.Repository.UsuarioRepository;

import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private UsuarioRepository repository;

    // Criação de usuário sem criptografar a senha
    @PostMapping
    public ResponseEntity<String> criarUsuario(@RequestBody Usuarios usuario) {
        Optional<Usuarios> usuarioExistente = repository.findByEmail(usuario.getEmail());
        if (usuarioExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário com este e-mail já existe");
        }

        // Salvar o usuário no banco de dados sem criptografia de senha
        repository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso");
    }

    // Atualizar o usuário e validar e-mails
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarUsuario(@PathVariable int id, @RequestBody Usuarios usuarioAtualizado) {
        return repository.findById(id)
            .map(usuario -> {
                Optional<Usuarios> usuarioExistente = repository.findByEmail(usuarioAtualizado.getEmail());
                if (usuarioExistente.isPresent() && usuarioExistente.get().getId() != id) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("E-mail já está em uso por outro usuário");
                }

                usuario.setNome(usuarioAtualizado.getNome());
                usuario.setEmail(usuarioAtualizado.getEmail());
                usuario.setSenha(usuarioAtualizado.getSenha()); 
                repository.save(usuario);
                return ResponseEntity.ok("Usuário atualizado com sucesso");
            })
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok("Usuário excluído com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<Usuarios>> getSelecionar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> buscarUsuarioPorId(@PathVariable int id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
