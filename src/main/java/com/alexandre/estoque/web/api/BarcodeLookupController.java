package com.alexandre.estoque.web.api;

import com.alexandre.estoque.integration.BarcodeClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/barcode")
@Tag(name="Barcode", description="Integração de código de barras (mock em dev)")
public class BarcodeLookupController {
    private final BarcodeClient client;
    public BarcodeLookupController(BarcodeClient client){ this.client = client; }

    @Operation(summary="Buscar dados por código de barras")
    @GetMapping("/{barcode}")
    public ResponseEntity<Map<String,Object>> lookup(@PathVariable String barcode){
        return client.fetchByBarcode(barcode)
                     .map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
