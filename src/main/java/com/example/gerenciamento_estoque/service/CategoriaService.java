package com.example.gerenciamento_estoque.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.gerenciamento_estoque.model.Categoria;
import com.example.gerenciamento_estoque.repository.CategoriaRepository;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public String salvar(String nome, String descricao) {
        if (categoriaRepository.existsByNome(nome)) {
            return "Já existe uma categoria com esse nome.";
        }
        Categoria categoria = new Categoria();
        categoria.setNome(nome);
        categoria.setDescricao(descricao);
        categoriaRepository.save(categoria);
        return null;
    }

    public void atualizar(Long id, String nome, String descricao) {
        categoriaRepository.findById(id).ifPresent(c -> {
            c.setNome(nome);
            c.setDescricao(descricao);
            categoriaRepository.save(c);
        });
    }

    public void deletar(Long id) {
        categoriaRepository.deleteById(id);
    }
}