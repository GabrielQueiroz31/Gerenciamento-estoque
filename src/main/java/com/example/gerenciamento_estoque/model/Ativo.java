package com.example.gerenciamento_estoque.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ativos")
public class Ativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 255)
    private String descricao;

    @Column(nullable = false, unique = true, length = 50)
    private String numeroPatrimonio;

    @Column(nullable = false)
    private String localizacao;

    @Column(nullable = false)
    private String estado; // BOM, REGULAR, RUIM, INATIVO

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getNumeroPatrimonio() { return numeroPatrimonio; }
    public void setNumeroPatrimonio(String numeroPatrimonio) { this.numeroPatrimonio = numeroPatrimonio; }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}