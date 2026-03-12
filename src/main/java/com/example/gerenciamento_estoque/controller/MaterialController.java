package com.example.gerenciamento_estoque.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.gerenciamento_estoque.service.CategoriaService;
import com.example.gerenciamento_estoque.service.MaterialService;

@Controller
@RequestMapping("/materiais")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private CategoriaService categoriaService;

    private boolean isLogado(HttpSession session) {
        Object logado = session.getAttribute("usuarioLogado");
        return logado != null && (Boolean) logado;
    }

    @GetMapping
    public String listar(HttpSession session, Model model) {
        if (!isLogado(session)) return "redirect:/login";
        model.addAttribute("materiais", materialService.listarTodos());
        return "materiais/lista";
    }

    @GetMapping("/novo")
    public String novoForm(HttpSession session, Model model) {
        if (!isLogado(session)) return "redirect:/login";
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "materiais/form";
    }

    @PostMapping("/salvar")
    public String salvar(@RequestParam String nome,
                         @RequestParam(required = false) String descricao,
                         @RequestParam Integer quantidade,
                         @RequestParam Long categoriaId,
                         HttpSession session) {
        if (!isLogado(session)) return "redirect:/login";
        materialService.salvar(nome, descricao, quantidade, categoriaId);
        return "redirect:/materiais";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, HttpSession session, Model model) {
        if (!isLogado(session)) return "redirect:/login";
        materialService.buscarPorId(id).ifPresent(m -> model.addAttribute("material", m));
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "materiais/form";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Long id,
                            @RequestParam String nome,
                            @RequestParam(required = false) String descricao,
                            @RequestParam Integer quantidade,
                            @RequestParam Long categoriaId,
                            HttpSession session) {
        if (!isLogado(session)) return "redirect:/login";
        materialService.atualizar(id, nome, descricao, quantidade, categoriaId);
        return "redirect:/materiais";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id, HttpSession session) {
        if (!isLogado(session)) return "redirect:/login";
        materialService.deletar(id);
        return "redirect:/materiais";
    }
}