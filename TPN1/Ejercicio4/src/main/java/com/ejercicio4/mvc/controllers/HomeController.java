package com.ejercicio4.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador para la página principal de la aplicación.
 */
@Controller
public class HomeController {

    /**
     * @GetMapping("/"): Mapea la raíz de la aplicación (http://localhost:8080/).
     * Retorna la vista index.html que actuará como Menú Principal.
     */
    @GetMapping("/")
    public String inicio() {
        return "index";
    }
}
