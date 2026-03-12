package com.example.gerenciamento_estoque.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.gerenciamento_estoque.service.AtivoService;

@Controller
@RequestMapping("/ativos")
public class AtivoController {

    @Autowired
    private AtivoService ativoService;

    private boolean isLogado(HttpSession session) {
        Object logado = session.getAttribute("usuarioLogado");
        return logado != null && (Boolean) logado;
    }

    @GetMapping
    public String listar(HttpSession session, Model model) {
        if (!isLogado(session)) return "redirect:/login";
        model.addAttribute("ativos", ativoService.listarTodos());
        return "ativos/lista";
    }

    @GetMapping("/novo")
    public String novoForm(HttpSession session) {
        if (!isLogado(session)) return "redirect:/login";
        return "ativos/form";
    }

    @PostMapping("/salvar")
    public String salvar(@RequestParam String nome,
                         @RequestParam(required = false) String descricao,
                         @RequestParam String numeroPatrimonio,
                         @RequestParam String localizacao,
                         @RequestParam String estado,
                         HttpSession session,
                         Model model) {
        if (!isLogado(session)) return "redirect:/login";

        String erro = ativoService.salvar(nome, descricao,
                numeroPatrimonio, localizacao, estado);
        if (erro != null) {
            model.addAttribute("erro", erro);
            return "ativos/form";
        }

        return "redirect:/ativos";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, HttpSession session, Model model) {
        if (!isLogado(session)) return "redirect:/login";
        ativoService.buscarPorId(id).ifPresent(a -> model.addAttribute("ativo", a));
        return "ativos/form";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Long id,
                            @RequestParam String nome,
                            @RequestParam(required = false) String descricao,
                            @RequestParam String numeroPatrimonio,
                            @RequestParam String localizacao,
                            @RequestParam String estado,
                            HttpSession session) {
        if (!isLogado(session)) return "redirect:/login";
        ativoService.atualizar(id, nome, descricao, numeroPatrimonio, localizacao, estado);
        return "redirect:/ativos";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id, HttpSession session) {
        if (!isLogado(session)) return "redirect:/login";
        ativoService.deletar(id);
        return "redirect:/ativos";
    }
}