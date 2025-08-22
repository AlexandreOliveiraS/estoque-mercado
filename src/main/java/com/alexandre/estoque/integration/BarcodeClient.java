package com.alexandre.estoque.integration;

import java.util.Map;
import java.util.Optional;

public interface BarcodeClient {
    Optional<Map<String,Object>> fetchByBarcode(String barcode);
}
