package com.alexandre.estoque.web.api;

import com.alexandre.estoque.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/export")
public class ExportController {
    private final ProductService service;
    public ExportController(ProductService service){ this.service = service; }

    @GetMapping(value="/products.csv", produces="text/csv")
    public void export(HttpServletResponse response) throws IOException {
        response.setHeader("Content-Disposition", "attachment; filename=products.csv");
        var w = response.getWriter();
        w.println("id,name,barcode,quantity,minQuantity,price");
        for (var p : service.list()) {
            w.printf("%d,%s,%s,%d,%d,%s%n",
                p.getId(), p.getName(), p.getBarcode(), p.getQuantity(), p.getMinQuantity(), p.getPrice());
        }
        w.flush();
    }
}
