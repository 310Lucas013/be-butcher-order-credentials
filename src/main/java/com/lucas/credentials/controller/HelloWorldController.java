package com.lucas.credentials.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin()
public class HelloWorldController {
    @GetMapping(value = "/greeting")
    public String getEmployees() {
        return "Welcome!";
    }
}
