package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Main {

    @GetMapping("/somar/{a}/{b}")
    public String somar(@PathVariable("a") double a, @PathVariable("b") double b) { // corrigi o path variable que
                                                                                    // estava com erro na rota
        return "Resultado: " + (a + b);
    }

    @GetMapping("/subtrair/{a}/{b}")
    public String subtrair(@PathVariable("a") double a, @PathVariable("b") double b) {
        return "Resultado: " + (a - b);
    }

    @GetMapping("/multiplicar/{a}/{b}")
    public String multiplicar(@PathVariable("a") double a, @PathVariable("b") double b) {
        return "Resultado: " + (a * b);
    }

    @GetMapping("/dividir/{a}/{b}")
    public String dividir(@PathVariable("a") double a, @PathVariable("b") double b) {
        if (b == 0) {
            return "Erro: Divisão por zero!";
        }
        return "Resultado: " + (a / b);
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
