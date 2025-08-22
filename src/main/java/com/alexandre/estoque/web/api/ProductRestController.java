package com.alexandre.estoque.web.api;

import com.alexandre.estoque.domain.Product;
import com.alexandre.estoque.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/products")
@Tag(name="Products", description="Operações de produtos e estoque")
@Validated
public class ProductRestController {

    private final ProductService service;
    public ProductRestController(ProductService service){ this.service = service; }

    public record ProductDTO(Long id, String name, String barcode, Integer quantity, Integer minQuantity, BigDecimal price) {
        public static ProductDTO from(Product p){
            return new ProductDTO(p.getId(), p.getName(), p.getBarcode(), p.getQuantity(), p.getMinQuantity(), p.getPrice());
        }
    }
    public record ProductCreateRequest(
            @NotBlank String name, @NotBlank String barcode,
            @NotNull @Min(0) Integer quantity, @NotNull @Min(0) Integer minQuantity,
            @NotNull @DecimalMin("0.00") BigDecimal price){}

    @Operation(summary="Listar produtos (paginado)")
    @GetMapping
    public Page<ProductDTO> list(@ParameterObject Pageable pageable){
        return service.list().stream()
                .map(ProductDTO::from)
                .collect(java.util.stream.Collectors.collectingAndThen(
                        java.util.stream.Collectors.toList(),
                        list -> new org.springframework.data.domain.PageImpl<>(list, pageable, list.size())
                ));
        // simples: converte lista em Page (pode trocar por service.listPaged(pageable) se implementar)
    }

    @Operation(summary="Criar produto")
    @PostMapping
    public ResponseEntity<ProductDTO> create(@Valid @RequestBody ProductCreateRequest req){
        Product p = new Product(req.name(), req.barcode(), req.quantity(), req.minQuantity(), req.price());
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductDTO.from(service.save(p)));
    }

    @Operation(summary="Entrada de estoque")
    @PostMapping("/{id}/entrada")
public ProductDTO entrada(@PathVariable("id") Long id, @RequestParam("qtd") int qtd){
        service.adjustStock(id, qtd);
        return ProductDTO.from(service.get(id));
    }

    @Operation(summary="Saída de estoque")
    @PostMapping("/{id}/saida")
public ProductDTO saida(@PathVariable("id") Long id, @RequestParam("qtd") int qtd){
        service.adjustStock(id, -qtd);
        return ProductDTO.from(service.get(id));
    }
}
