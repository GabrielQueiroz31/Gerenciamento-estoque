package com.example.gerenciamento_estoque.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.gerenciamento_estoque.model.Funcionario;
import com.example.gerenciamento_estoque.repository.FuncionarioAutenticadoRepository;
import com.example.gerenciamento_estoque.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncionarioAutenticadoRepository funcionarioAutenticadoRepository;

    public boolean login(String nif, String senha) {
        return funcionarioRepository.findByNif(nif)
                .map(f -> f.getSenha().equals(senha) && f.isAtivo())
                .orElse(false);
    }

    public String cadastrar(String nome, String nif, String senha) {
        // Verifica se está na lista branca
        if (!funcionarioAutenticadoRepository.existsByNifAndNomeAndAtivoTrue(nif, nome)) {
            return "NIF e nome não estão autorizados para cadastro.";
        }

        // Verifica se NIF já tem conta
        if (funcionarioRepository.existsByNif(nif)) {
            return "Este NIF já possui uma conta cadastrada.";
        }

        Funcionario novo = new Funcionario();
        novo.setNome(nome);
        novo.setNif(nif);
        novo.setSenha(senha);
        funcionarioRepository.save(novo);

        return null; // null = sem erro
    }
}