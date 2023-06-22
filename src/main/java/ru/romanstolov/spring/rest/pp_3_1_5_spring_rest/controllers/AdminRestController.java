package ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.models.Role;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.models.User;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.services.RoleService;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.services.UserService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
@Secured("ROLE_ADMIN")
public class AdminRestController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/roles")
    public ResponseEntity<Collection<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/users")
    public ResponseEntity<HttpStatus> addUser(@RequestBody User newUser, @RequestParam("roles") String[] roles) {
        newUser.setRoles(roleService.createCollectionRoles(roles));
        userService.save(newUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user) {
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
