// src/main/java/com/alexandre/estoque/integration/impl/BarcodeClientMock.java
package com.alexandre.estoque.integration.impl;

import com.alexandre.estoque.integration.BarcodeClient;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@Primary
@Profile({"dev","prod"})   // disponível tanto no dev quanto no prod
public class BarcodeClientMock implements BarcodeClient {

    @Override
    public Optional<Map<String, Object>> fetchByBarcode(String barcode) {
        return Optional.of(Map.of(
            "name", "Produto Mock",
            "barcode", barcode,
            "brand", "ACME",
            "price", 19.90
        ));
    }
}
