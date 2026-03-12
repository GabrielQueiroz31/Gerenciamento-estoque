package com.example.gerenciamento_estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.gerenciamento_estoque.model.Ativo;

public interface AtivoRepository extends JpaRepository<Ativo, Long> {
    boolean existsByNumeroPatrimonio(String numeroPatrimonio);
}