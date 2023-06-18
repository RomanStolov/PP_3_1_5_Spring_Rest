package ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.models.Role;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.models.User;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.services.UserServiceImpl;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@RestController
public class PeopleControllerRest {
    private final UserServiceImpl userService;

    @Autowired
    public PeopleControllerRest(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/admin/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/admin/roles")
    public ResponseEntity<Collection<Role>> getAllRoles() {
        return new ResponseEntity<>(userService.getListRole(), HttpStatus.OK);
    }

    @GetMapping(value = "/admin/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/user")
    public ResponseEntity<User> getUser(Principal principal) {
        return new ResponseEntity<>(userService.findUserByUsername(principal.getName()), HttpStatus.OK);
    }

    /**
     * Отправляем обратно HTTP ответ с пустым телом и статусом "200".
     * Решил с UserDTO и ModelMapper не усложнять функционал, так как об этом в таске не сказано.
     */
    @PostMapping(value = "/admin/users")
    public ResponseEntity<HttpStatus> addUser(@RequestBody User newUser, @RequestParam("roles") String[] roles) {
        newUser.setRoles(userService.createCollectionRoles(roles));
        userService.save(newUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Отправляем обратно HTTP ответ с пустым телом и статусом "200".
     * ПО ТРЕГУЛОВУ ЖЕЛАТЕЛЬНО УБРАТЬ ИЗ УРЛА "id"!
     * Но его оставил как по Алишеву...
     */
    @PutMapping(value = "/admin/users/{id}")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user) {
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Отправляем обратно HTTP ответ с пустым телом и статусом "200".
     */
    @DeleteMapping(value = "/admin/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
