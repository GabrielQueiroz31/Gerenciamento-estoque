package com.example.gerenciamento_estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.gerenciamento_estoque.model.Movimentacao;
import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    List<Movimentacao> findByMaterialIdOrderByDataHoraDesc(Long materialId);
}