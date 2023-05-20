package ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.models.Role;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.models.User;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.services.UserServiceImpl;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.utils.UserValidator;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Ссылка на главную страничку, чтоб в браузере пальчиками не корячиться...
 * http://localhost:8080/
 * Решил не заморачиваться с двумя контроллерами и запихал всё в один "развязав" доступ по пафу префиксами
 * админа и юзера.
 * <p>
 * Для проверки работоспособности программы с отображением разных ролей следующие пары логин-пароль:
 * - с ролью только админа: admin, admin;
 * - с ролью только пользователя: user, user;
 * - с ролью админа и пользователя: adminuser, adminuser.
 */
//@Controller
@RestController
public class PeopleController {
    private final UserServiceImpl userService;
    private final UserValidator userValidator;

    @Autowired
    public PeopleController(UserServiceImpl userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    /**
     * Метод рест возвращающий список всех пользователей при входе под учёткой админа
     */
    @GetMapping(value = "/admin/users")
    public List<User> showAllUsers() {
        return userService.findAll();
    }

    /**
     * Метод рест возвращающий данные текущего пользователя при входе под учёткой простого юзера
     */
    @GetMapping(value = "/user")
    public User showUser(Principal principal) {
        Optional<User> optionalUser = userService.findByUsername(principal.getName());
        User user = null;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        return user;
    }

    /**
     * Метод рест сохраняющий нового пользователя в БД
     */
    @PostMapping(value = "/admin/users")
    public void addUser(@RequestBody User newUser) {
        userService.save(newUser);
    }

    /**
     * Метод рест изменяющий данные пользователя в БД.
     * ПО ТРЕГУЛОВУ ЖЕЛАТЕЛЬНО УБРАТЬ ИЗ УРЛА "id"!!!!!!!!!!!!!!!!
     */
    @PutMapping(value = "/admin/users/{id}")
    public void editUser(@RequestBody User user) {
        userService.update(user);
    }

    /**
     * Метод рест удаляющий пользователя из БД.
     */
    @DeleteMapping(value = "/admin/users/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    /**
     * Метод возвращающий страницу регистрации нового пользователя на сайте.
     */
    @GetMapping(value = "/registration")
    public String registrationForm(@ModelAttribute(value = "registrUser") User registrUser) {
        return "registration";
    }

    /**
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

    /**
     * Метод возвращающий страницу администратора.
     * В модель загрузил:
     * - список всех пользователей;
     * - текущего пользователя-админа с проверкой на "null" и извлечением из Optional;
     * - нового пользователя для создания;
     * - список всех возможных ролей.
     */
//    @GetMapping(value = "/admin/users")
    public String getAdminPage(Model model, Principal principal) {
        List<User> listUsers = userService.findAll();
        model.addAttribute("listUsers", listUsers);
        Optional<User> optionalUser = userService.findByUsername(principal.getName());
        User user = null;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        model.addAttribute("user", user);
        model.addAttribute("newUser", new User());
        Collection<Role> listAllRoles = userService.getListRole();
        model.addAttribute("listAllRoles", listAllRoles);
        return "admin/admin";
    }

    /**
     * Метод возвращающий страницу простого пользователя.
     */
//    @GetMapping(value = "/user")
    public String getUserPage(Model model, Principal principal) {
        Optional<User> optionalUser = userService.findByUsername(principal.getName());
        User user = null;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        model.addAttribute("user", user);
        return "user/user";
    }

    /**
     * Метод администратора сохраняющий нового пользователя в БД
     * Выполняется предварительная проверка на наличие пользователя в БД с таким же "Username" как и у вновь
     * регистрируемого пользователя (уникальность логина в БД). Если ошибки при вводе имени нового пользователя,
     * то сохранения нового пользователя не происходит!
     * В конце происходит редирект обратно на страницу админа.
     */
//    @PostMapping(value = "/admin/users")
    public String addUser(@ModelAttribute(value = "newUser") User newUser,
                          BindingResult bindingResult) {
        userValidator.validate(newUser, bindingResult);
        if (bindingResult.hasErrors()) {
            return "redirect:/admin/users";
        }
        userService.save(newUser);
        return "redirect:/admin/users";
    }

    /**
     * Метод администратора сохраняющий в БД измененную информацию о пользователе.
     * После сохранения отредактированного пользователя делаю редирект обратно на страницу админа.
     */
//    @PutMapping(value = "/admin/users/{id}")
    public String oldEditUser(@ModelAttribute(value = "user") User user) {
        userService.update(user);
        return "redirect:/admin/users";
    }

    /**
     * Метод администратора удаляющий пользователя из БД.
     * Использовал "id" удаляемого пользователя для создания нового пользователя и затем отправки его
     * с этим установленным "id" в сервис на удаление.
     * После удаления пользователя по "id" делаю редирект обратно на страницу админа.
     */
//    @DeleteMapping(value = "/admin/users/{id}")
    public String oldDeleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }

}
