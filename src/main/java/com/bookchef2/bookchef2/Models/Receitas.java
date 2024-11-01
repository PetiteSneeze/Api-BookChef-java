package com.bookchef2.bookchef2.Models;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Receitas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private String ingredientes;
    private String modoPreparo;
    private String imagemUrl;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "tipo_id")
    private TipoReceitas tipo;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuarios usuario;
}
