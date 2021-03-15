package com.codecool.barbershop.barbershop.configuration;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class HelloController {
    @GetMapping("/")
    public String hello (){
        Date today = new Date();
        String message = "The server is up and running " + today +
                " To access the app visit https://dashboardbarbershop.herokuapp.com/";
        return message;
    }
}
