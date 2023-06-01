package ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.models.Role;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.models.User;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.services.UserServiceImpl;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.utils.UserValidator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Оставил часть функционала в этом простом контроллере для загрузки самих страничек админа и пользователя,
 * а так же методы регистрации нового пользователя на сайте с главной страницы.
 * Весь остальной функционал написал в новом REST контроллере.
 */
@Controller
public class PeopleControllerNoRest {
    private final UserServiceImpl userService;
    private final UserValidator userValidator;

    @Autowired
    public PeopleControllerNoRest(UserServiceImpl userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    /**
     * Метод открывающий страницу админа при входе под админом.
     */
    @GetMapping(value = "/admin/page")
    public String getAdminPage() {
        return "admin/admin";
    }

    /**
     * Метод открывающий страницу пользователя при входе под пользователем.
     */
    @GetMapping(value = "/user/page")
    public String getUserPage() {
        return "user/user";
    }

    /**
     * В задаче 3.1.5 ОСТАВИЛ !!!
     * Метод возвращающий страницу регистрации нового пользователя с главной страницы сайта.
     */
    @GetMapping(value = "/registration")
    public String registrationForm(@ModelAttribute(value = "registrUser") User registrUser) {
        return "registration";
    }

    /**
     * В задаче 3.1.5 ОСТАВИЛ !!!
     * Метод сохраняющий нового пользователя в БД со страницы регистрации нового пользователя на сайте.
     * По умолчанию автоматически присваивается новому пользователю при регистрации на сайте роль "ROLE_USER".
     * Ещё дополнительно реализована валидация пользователя по уникальности имени (логина).
     * Если допущены ошибки при вводе имени нового пользователя, то происходит возврат обратно на форму регистрации!
     */
    @PostMapping(value = "/registration")
    public String registrationNewUser(@Validated @ModelAttribute(value = "registrUser") User registrUser,
                                      BindingResult bindingResult) {
        Role role = new Role("ROLE_USER");
        Collection<Role> roles = new ArrayList<>();
        roles.add(role);
        registrUser.setRoles(roles);
        userValidator.validate(registrUser, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.save(registrUser);
        return "redirect:/login";
    }

}
