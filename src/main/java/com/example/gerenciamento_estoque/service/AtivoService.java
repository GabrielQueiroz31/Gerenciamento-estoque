package com.example.gerenciamento_estoque.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.gerenciamento_estoque.model.Ativo;
import com.example.gerenciamento_estoque.repository.AtivoRepository;
import java.util.List;
import java.util.Optional;

@Service
public class AtivoService {

    @Autowired
    private AtivoRepository ativoRepository;

    public List<Ativo> listarTodos() {
        return ativoRepository.findAll();
    }

    public Optional<Ativo> buscarPorId(Long id) {
        return ativoRepository.findById(id);
    }

    public String salvar(String nome, String descricao,
                         String numeroPatrimonio, String localizacao, String estado) {
        if (ativoRepository.existsByNumeroPatrimonio(numeroPatrimonio)) {
            return "Já existe um ativo com esse número de patrimônio.";
        }
        Ativo ativo = new Ativo();
        ativo.setNome(nome);
        ativo.setDescricao(descricao);
        ativo.setNumeroPatrimonio(numeroPatrimonio);
        ativo.setLocalizacao(localizacao);
        ativo.setEstado(estado);
        ativoRepository.save(ativo);
        return null;
    }

    public void atualizar(Long id, String nome, String descricao,
                          String numeroPatrimonio, String localizacao, String estado) {
        ativoRepository.findById(id).ifPresent(a -> {
            a.setNome(nome);
            a.setDescricao(descricao);
            a.setNumeroPatrimonio(numeroPatrimonio);
            a.setLocalizacao(localizacao);
            a.setEstado(estado);
            ativoRepository.save(a);
        });
    }

    public void deletar(Long id) {
        ativoRepository.deleteById(id);
    }
}