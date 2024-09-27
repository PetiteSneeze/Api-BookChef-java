package com.bookchef2.bookchef2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Método para criar usuário com senha criptografada
    @PostMapping
    public ResponseEntity<String> criarUsuario(@RequestBody Usuarios usuario) {
        Optional<Usuarios> usuarioExistente = repository.findByEmail(usuario.getEmail());
        if (usuarioExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário com este e-mail já existe");
        }
        // Criptografar a senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        repository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso");
    }

    // Método para atualizar usuário, incluindo verificação de duplicidade de e-mail e criptografia de senha
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarUsuario(@PathVariable int id, @RequestBody Usuarios usuarioAtualizado) {
        return repository.findById(id)
            .map(usuario -> {
                Optional<Usuarios> usuarioExistente = repository.findByEmail(usuarioAtualizado.getEmail());
                if (usuarioExistente.isPresent() && usuarioExistente.get().getId() != id) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("E-mail já está em uso por outro usuário");
                }

                // Atualizar nome, e-mail e senha (criptografada)
                usuario.setNome(usuarioAtualizado.getNome());
                usuario.setEmail(usuarioAtualizado.getEmail());
                usuario.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha()));
                repository.save(usuario);
                return ResponseEntity.ok("Usuário atualizado com sucesso");
            })
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // Método para excluir usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok("Usuário excluído com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    }

    // Método para buscar todos os usuários
    @GetMapping
    public ResponseEntity<Iterable<Usuarios>> getSelecionar() {
        return ResponseEntity.ok(repository.findAll());
    }

    // Método para buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> buscarUsuarioPorId(@PathVariable int id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Método para login com verificação de senha criptografada
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuarios loginRequest) {
        Optional<Usuarios> usuarioOpt = repository.findByEmail(loginRequest.getEmail());
        if (usuarioOpt.isPresent()) {
            Usuarios usuario = usuarioOpt.get();
            // Verifique a senha criptografada usando BCryptPasswordEncoder
            if (passwordEncoder.matches(loginRequest.getSenha(), usuario.getSenha())) {
                return ResponseEntity.ok("Login realizado com sucesso!");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("E-mail ou senha inválidos!");
    }
}
