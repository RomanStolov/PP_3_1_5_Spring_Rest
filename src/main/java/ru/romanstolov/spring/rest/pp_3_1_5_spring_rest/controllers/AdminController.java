package ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping(value = "/admin")
    public String getAdminPage() {
        return "admin";
    }

}
