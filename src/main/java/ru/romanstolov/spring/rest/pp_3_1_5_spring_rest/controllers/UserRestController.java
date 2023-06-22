package ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.models.User;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.services.UserService;

import java.security.Principal;

@RestController
@RequestMapping(value = "/api")
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public ResponseEntity<User> getUser(Principal principal) {
        return new ResponseEntity<>(userService.findByUsername(principal.getName()), HttpStatus.OK);
    }

}
