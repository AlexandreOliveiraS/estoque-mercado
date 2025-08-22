// src/main/java/com/alexandre/estoque/web/HomeController.java
package com.alexandre.estoque.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() { return "redirect:/products"; }
}
