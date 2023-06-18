package ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.models.Role;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.models.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserService {


    Collection<Role> getListRole();

    User findUserByUsername(String username);

    List<User> findAll();

    User findUserById(Long id);

    void save(User user);

    void update(User user);

    void delete(Long id);

    /**
     * Добавил в таске 3.1.5
     */
    Collection<Role> createCollectionRoles(String[] roles);

}

