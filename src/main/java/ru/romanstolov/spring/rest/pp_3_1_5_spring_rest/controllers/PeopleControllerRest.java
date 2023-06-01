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
import java.util.Optional;

/**
 * Ссылка на главную страничку, чтоб в браузере пальчиками не корячиться...
 * http://localhost:8080/
 * Ссылки другие для проверки работоспособности в том же постмане:
 * http://localhost:8080/user
 * http://localhost:8080/admin/users
 * Вынес часть методов (для загрузки самих страничек админа и пользователя, а так же методы регистрации
 * нового пользователя на сайте) в этот отдельный контроллер. Его сделал REST добавив аннотацию
 * @RestController. Доступ к функционалу по-прежнему "развязан" по пафу префиксами админа и юзера.
 * <p>
 * Для проверки работоспособности программы с отображением разных ролей следующие пары логин-пароль:
 * - с ролью только админа: admin, admin;
 * - с ролью только пользователя: user, user;
 * - с ролью админа и пользователя: adminuser, adminuser.
 * <p>
 * НЕ СТАЛ ДОБАВЛЯТЬ СЛЕДУЮЩИЙ ФУНКЦИОНАЛ, так как об этом в задаче не сказано:
 * - валидацию данных со стороны сервера как на сами данные, так и на наличие пользователя с уже
 * существующим таким же логином (в прошлых задачах это делал, а в этой убрал для ускорения тестирования);
 * - создавать кастомные исключения с последующей их генерацией, перехватом и последующей отправкой
 * ResponseEntity соответственно с HttpStatus.NOT_FOUND или HttpStatus.BAD_REQUEST;
 * - создавать отдельный класс UserDTO и использовать ModelMapper для того чтобы "развязать" нашу
 * модель с объектом отправляемым по сети в виде джейсона.
 */
@RestController
public class PeopleControllerRest {
    private final UserServiceImpl userService;

    @Autowired
    public PeopleControllerRest(UserServiceImpl userService) {
        this.userService = userService;
    }

    /**
     * Метод возвращающий список всех пользователей при входе под учёткой админа.
     */
    @GetMapping(value = "/admin/users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    /**
     * Метод возвращающий список всех Ролей при входе под учёткой админа.
     */
    @GetMapping(value = "/admin/roles")
    public Collection<Role> getAllRoles() {
        return userService.getListRole();
    }

    /**
     * Метод возвращающий данные пользователя из БД по "id" при входе под учёткой админа.
     */
    @GetMapping(value = "/admin/users/{id}")
    public User editUser(@PathVariable(value = "id") Long id) {
        return userService.findById(id);
    }

    /**
     * Метод возвращающий данные текущего пользователя (для заполнения навбара в шапке
     * страницы и для заполнения таблицы с информацией о текущем пользователе). Нужен как для
     * админа, так и для простого пользователя.
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
     * Метод сохраняющий нового пользователя в БД при входе под учёткой админа.
     * Отправляем обратно HTTP ответ с пустым телом и статусом "200".
     * Решил с UserDTO и ModelMapper не усложнять функционал, так как об этом в таске не сказано.
     */
    @PostMapping(value = "/admin/users")
    public ResponseEntity<HttpStatus> addUser(@RequestBody User newUser, @RequestParam("roles") String[] roles) {
        newUser.setRoles(userService.createCollectionRoles(roles));
        userService.save(newUser);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Метод изменяющий данные пользователя в БД при входе под учёткой админа.
     * Отправляем обратно HTTP ответ с пустым телом и статусом "200".
     * ПО ТРЕГУЛОВУ ЖЕЛАТЕЛЬНО УБРАТЬ ИЗ УРЛА "id"!
     * Но его оставил как по Алишеву...
     */
    @PutMapping(value = "/admin/users/{id}")
    public ResponseEntity<HttpStatus> editUser(@RequestBody User user) {
        userService.update(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Метод удаляющий пользователя из БД при входе под учёткой админа.
     * Отправляем обратно HTTP ответ с пустым телом и статусом "200".
     */
    @DeleteMapping(value = "/admin/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
