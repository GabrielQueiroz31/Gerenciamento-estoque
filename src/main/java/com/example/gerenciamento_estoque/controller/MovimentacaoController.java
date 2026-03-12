package com.example.gerenciamento_estoque.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.gerenciamento_estoque.service.MaterialService;
import com.example.gerenciamento_estoque.service.MovimentacaoService;

@Controller
@RequestMapping("/movimentacoes")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService movimentacaoService;

    @Autowired
    private MaterialService materialService;

    private boolean isLogado(HttpSession session) {
        Object logado = session.getAttribute("usuarioLogado");
        return logado != null && (Boolean) logado;
    }

    @GetMapping
    public String listar(HttpSession session, Model model) {
        if (!isLogado(session)) return "redirect:/login";
        model.addAttribute("movimentacoes", movimentacaoService.listarTodas());
        return "movimentacoes/lista";
    }

    @GetMapping("/nova")
    public String novaForm(HttpSession session, Model model) {
        if (!isLogado(session)) return "redirect:/login";
        model.addAttribute("materiais", materialService.listarTodos());
        return "movimentacoes/form";
    }

    @PostMapping("/salvar")
public String salvar(@RequestParam(required = false) Long materialId,
                     @RequestParam(required = false) String tipo,
                     @RequestParam(required = false) Integer quantidade,
                     @RequestParam(required = false) String observacao,
                     HttpSession session,
                     Model model) {
    if (!isLogado(session)) return "redirect:/login";

    // Log temporário para debug
    System.out.println(">>> materialId: " + materialId);
    System.out.println(">>> tipo: " + tipo);
    System.out.println(">>> quantidade: " + quantidade);

    if (materialId == null) {
        model.addAttribute("erro", "materialId chegou nulo!");
        model.addAttribute("materiais", materialService.listarTodos());
        return "movimentacoes/form";
    }

    String erro = movimentacaoService.registrar(materialId, tipo, quantidade, observacao);
    if (erro != null) {
        model.addAttribute("erro", erro);
        model.addAttribute("materiais", materialService.listarTodos());
        return "movimentacoes/form";
    }

    return "redirect:/movimentacoes";
}
}