package ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.models.Role;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.models.User;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.services.UserServiceImpl;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Ссылка на главную страничку, чтоб в браузере пальчиками не корячиться...
 * http://localhost:8080/
 * Ссылки другие для проверки работоспособности в том же постмане:
 * http://localhost:8080/user
 * http://localhost:8080/admin/users
 * Решил не заморачиваться с двумя контроллерами и запихал всё в один "развязав" доступ по пафу префиксами
 * админа и юзера.
 * <p>
 * Для проверки работоспособности программы с отображением разных ролей следующие пары логин-пароль:
 * - с ролью только админа: admin, admin;
 * - с ролью только пользователя: user, user;
 * - с ролью админа и пользователя: adminuser, adminuser.
 */
@RestController
public class PeopleControllerRest {
    private final UserServiceImpl userService;

    @Autowired
    public PeopleControllerRest(UserServiceImpl userService) {
        this.userService = userService;
    }

    /**
     * В задаче 3.1.5 ДОБАВИЛ !!!
     * Метод рест возвращающий список всех пользователей при входе под учёткой админа
     */
    @GetMapping(value = "/admin/users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    /**
     * В задаче 3.1.5 ДОБАВИЛ !!!
     * Метод рест возвращающий список всех Ролей при входе под учёткой админа
     */
    @GetMapping(value = "/admin/roles")
    public Collection<Role> getAllRoles() {
        return userService.getListRole();
    }

    /**
     * В задаче 3.1.5 ДОБАВИЛ !!!
     * Метод рест возвращающий данные текущего пользователя (для заполнения навбара в шапке
     * страницы и для заполнения таблицы с информацией о текущем пользователе)
     */
    @GetMapping(value = "/user")
    public User getUser(Principal principal) {
        Optional<User> optionalUser = userService.findByUsername(principal.getName());
        User user = null;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        return user;
    }

    /**
     * В задаче 3.1.5 ДОБАВИЛ !!!
     * Метод рест сохраняющий нового пользователя в БД
     */
    @PostMapping(value = "/admin/users")
    public void addUser(@RequestBody User newUser, @RequestParam(value = "roles") String[] roles) {
        newUser.setRoles(userService.createCollectionRoles(roles));
        userService.save(newUser);
    }

    /**
     * В задаче 3.1.5 ДОБАВИЛ !!!
     * Метод рест изменяющий данные пользователя в БД.
     * ПО ТРЕГУЛОВУ ЖЕЛАТЕЛЬНО УБРАТЬ ИЗ УРЛА "id"!!!!!!!!!!!!!!!!
     * Но его оставил как по Алишеву...
     */
    @PutMapping(value = "/admin/users/{id}")
    public void editUser(@RequestBody User user) {
        userService.update(user);
    }

    /**
     * В задаче 3.1.5 ДОБАВИЛ !!!
     * Метод рест возвращающий данные пользователя из БД по "id".
     */
    @GetMapping(value = "/admin/users/{id}")
    public User editUser(@PathVariable(value = "id") Long id) {
        return userService.getById(id);
    }

    /**
     * В задаче 3.1.5 ДОБАВИЛ !!!
     * Метод рест удаляющий пользователя из БД.
     */
    @DeleteMapping(value = "/admin/users/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
    }

}
