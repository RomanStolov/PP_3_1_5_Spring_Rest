package ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.models.Role;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.models.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Согласно условию таски, добавил имплементацию "UserDetailsService" с одним методом
 * "public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException",
 * который реализовал в "UserServiceImpl".
 * <p>
 * @see UserServiceImpl#loadUserByUsername(String)
 * <p>
 * Этот метод возвращает UserDetails - обёрнутого пользователя текущей сессии.
 */
public interface UserService extends UserDetailsService {


    Collection<Role> getListRole();

    /**
     * Добавил новый метод поиска пользователя по "username"
     * Его продублировал в интерфейс "UserRepository"
     */
    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    List<User> findAll();

    void save(User user);

    void update(User user);

    User getById(Long id);

    void delete(User deleteUser);

}

