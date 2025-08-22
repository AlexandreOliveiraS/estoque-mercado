package com.alexandre.estoque.service;

import com.alexandre.estoque.domain.Product;
import com.alexandre.estoque.repo.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repo;
    public ProductService(ProductRepository repo) { this.repo = repo; }

    public List<Product> list() { return repo.findAll(); }

    public Product get(Long id) { return repo.findById(id).orElseThrow(); }

    public Product save(Product p) {
        if (p.getQuantity() == null) p.setQuantity(0);
        if (p.getMinQuantity() == null) p.setMinQuantity(0);
        return repo.save(p);
    }

    @Transactional
    public void adjustStock(Long id, int delta) {
        Product p = get(id);
        int newQty = p.getQuantity() + delta;
        if (newQty < 0) throw new IllegalArgumentException("Estoque insuficiente");
        p.setQuantity(newQty);
    }

    public List<Product> lowStockAlerts() {
        return repo.findAll().stream()
                .filter(p -> p.getQuantity() <= p.getMinQuantity())
                .toList();
    }
}
