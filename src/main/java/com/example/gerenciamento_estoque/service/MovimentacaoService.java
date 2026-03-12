package com.example.gerenciamento_estoque.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.gerenciamento_estoque.model.Movimentacao;
import com.example.gerenciamento_estoque.repository.MaterialRepository;
import com.example.gerenciamento_estoque.repository.MovimentacaoRepository;
import java.util.List;

@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private MaterialRepository materialRepository;

    public List<Movimentacao> listarTodas() {
        return movimentacaoRepository.findAll();
    }

    public String registrar(Long materialId, String tipo,
                            Integer quantidade, String observacao) {
        return materialRepository.findById(materialId).map(material -> {

            // Valida quantidade para SAIDA
            if (tipo.equals("SAIDA") && material.getQuantidade() < quantidade) {
                return "Quantidade insuficiente em estoque. Disponível: "
                        + material.getQuantidade();
            }

            // Atualiza estoque
            if (tipo.equals("ENTRADA")) {
                material.setQuantidade(material.getQuantidade() + quantidade);
            } else {
                material.setQuantidade(material.getQuantidade() - quantidade);
            }
            materialRepository.save(material);

            // Registra movimentação
            Movimentacao mov = new Movimentacao();
            mov.setMaterial(material);
            mov.setTipo(tipo);
            mov.setQuantidade(quantidade);
            mov.setObservacao(observacao);
            movimentacaoRepository.save(mov);

            return null; // null = sem erro
        }).orElse("Material não encontrado.");
    }

    public void deletar(Long id) {
        movimentacaoRepository.deleteById(id);
    }
}