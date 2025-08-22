package com.alexandre.estoque.web;

import com.alexandre.estoque.domain.Product;
import com.alexandre.estoque.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;
    public ProductController(ProductService service) { this.service = service; }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", service.list());
        model.addAttribute("alerts", service.lowStockAlerts());
        model.addAttribute("product", new Product("", "", 0, 0, new BigDecimal("0.00")));
        return "products/list";
    }

    @PostMapping
    public String create(@ModelAttribute Product product) {
        service.save(product);
        return "redirect:/products";
    }

    @PostMapping("/{id}/entrada")
public String entrada(@PathVariable("id") Long id, @RequestParam("qtd") int qtd) {
    service.adjustStock(id, qtd);
    return "redirect:/products";
}

@PostMapping("/{id}/saida")
public String saida(@PathVariable("id") Long id, @RequestParam("qtd") int qtd) {
    service.adjustStock(id, -qtd);
    return "redirect:/products";
}
}
