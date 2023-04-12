package com.se.bookingmanagementsystembackend.apis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestAPI {
    @GetMapping
    public String testing(){
        return "Hello from test API";
    }
}
