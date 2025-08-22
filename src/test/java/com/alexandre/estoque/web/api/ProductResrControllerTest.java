package com.alexandre.estoque.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductRestControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    void criaELista() throws Exception {
        var body = Map.of(
                "name","Teste API","barcode","999000111",
                "quantity",5,"minQuantity",2,"price", new BigDecimal("12.34")
        );

        mvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(body)))
                .andExpect(status().isCreated());

        mvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists());
    }
}
