package com.example.gerenciamento_estoque.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.gerenciamento_estoque.model.Categoria;
import com.example.gerenciamento_estoque.service.CategoriaService;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    private boolean isLogado(HttpSession session) {
        Object logado = session.getAttribute("usuarioLogado");
        return logado != null && (Boolean) logado;
    }

    @GetMapping
    public String listar(HttpSession session, Model model) {
        if (!isLogado(session)) return "redirect:/login";
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "categorias/lista";
    }

    @GetMapping("/nova")
    public String novaForm(HttpSession session, Model model) {
        if (!isLogado(session)) return "redirect:/login";
        model.addAttribute("categoria", new Categoria());
        return "categorias/form";
}

    @PostMapping("/salvar")
    public String salvar(@RequestParam String nome,
                         @RequestParam(required = false) String descricao,
                         HttpSession session,
                         Model model) {
        if (!isLogado(session)) return "redirect:/login";

        String erro = categoriaService.salvar(nome, descricao);
        if (erro != null) {
            model.addAttribute("erro", erro);
            return "categorias/form";
        }

        return "redirect:/categorias";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, HttpSession session, Model model) {
        if (!isLogado(session)) return "redirect:/login";
        categoriaService.buscarPorId(id).ifPresent(c -> model.addAttribute("categoria", c));
        return "categorias/form";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Long id,
                            @RequestParam String nome,
                            @RequestParam(required = false) String descricao,
                            HttpSession session) {
        if (!isLogado(session)) return "redirect:/login";
        categoriaService.atualizar(id, nome, descricao);
        return "redirect:/categorias";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id, HttpSession session) {
        if (!isLogado(session)) return "redirect:/login";
        categoriaService.deletar(id);
        return "redirect:/categorias";
    }
}