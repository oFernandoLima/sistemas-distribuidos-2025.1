package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Main {
    @RequestMapping("/")
    String home() {
        return "Olá, seja bem vindo ao teste de Spring Boot para disciplina de Sistemas Distribuídos";
    }

    @RequestMapping("/nome/{nome}")
    String piada(@PathVariable("nome") String nome) { //tive que corrigir o path variable que causava erro na rota
        return "Olá " + nome + ", você vai realmente conseguir passar nessa disciplina?";
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
