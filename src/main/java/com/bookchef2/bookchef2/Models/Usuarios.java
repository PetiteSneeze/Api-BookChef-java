package com.bookchef2.bookchef2.Models;

import java.util.List;  // Correta importação do java.util.List

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;
    private String email;
    private String senha;

    @JsonIgnore
    // Relacionamento OneToMany entre Usuario e Receitas
    @OneToMany(mappedBy = "usuario") 
    private List<Receitas> receitas; 
}
