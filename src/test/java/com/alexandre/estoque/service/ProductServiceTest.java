package com.alexandre.estoque.service;

import com.alexandre.estoque.domain.Product;
import com.alexandre.estoque.repo.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired ProductService service;
    @Autowired ProductRepository repo;

    @Test
    void naoPermiteEstoqueNegativo() {
        Product p = service.save(new Product("Arroz Teste","789123TEST",1,0,new BigDecimal("10.00")));
        assertThrows(IllegalArgumentException.class, () -> service.adjustStock(p.getId(), -2));
    }
}
