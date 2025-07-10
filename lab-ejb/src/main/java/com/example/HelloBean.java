package com.example;

import javax.ejb.Stateless;

@Stateless
public class HelloBean {
    public String sayHello(String name) {
        return "Ola " + name + ", este e um EJB!";
    }
}
