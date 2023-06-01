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
 * @see UserServiceImpl#loadUserByUsername
 * <p>
 * Этот метод возвращает UserDetails - обёрнутого пользователя текущей сессии.
 */
public interface UserService extends UserDetailsService {


    Collection<Role> getListRole();

    /**
     * Добавил в задаче 3.1.3 новый метод поиска пользователя по "username"
     * Его продублировал в интерфейс "UserRepository"
     */
    Optional<User> findByUsername(String username);

    List<User> findAll();

    void save(User user);

    void update(User user);

    User findById(Long id);

    void delete(Long id);

    /**
     * Добавил в таске 3.1.5
     */
    Collection<Role> createCollectionRoles(String[] roles);

}

