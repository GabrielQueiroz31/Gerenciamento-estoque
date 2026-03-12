package com.example.gerenciamento_estoque.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.gerenciamento_estoque.model.Material;
import com.example.gerenciamento_estoque.repository.CategoriaRepository;
import com.example.gerenciamento_estoque.repository.MaterialRepository;
import java.util.List;
import java.util.Optional;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Material> listarTodos() {
        return materialRepository.findAll();
    }

    public Optional<Material> buscarPorId(Long id) {
        return materialRepository.findById(id);
    }

    public void salvar(String nome, String descricao, Integer quantidade, Long categoriaId) {
        Material material = new Material();
        material.setNome(nome);
        material.setDescricao(descricao);
        material.setQuantidade(quantidade);
        categoriaRepository.findById(categoriaId)
                .ifPresent(material::setCategoria);
        materialRepository.save(material);
    }

    public void atualizar(Long id, String nome, String descricao, Integer quantidade, Long categoriaId) {
        materialRepository.findById(id).ifPresent(m -> {
            m.setNome(nome);
            m.setDescricao(descricao);
            m.setQuantidade(quantidade);
            categoriaRepository.findById(categoriaId)
                    .ifPresent(m::setCategoria);
            materialRepository.save(m);
        });
    }

    public void deletar(Long id) {
        materialRepository.deleteById(id);
    }
}