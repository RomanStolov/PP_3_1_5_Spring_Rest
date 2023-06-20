package ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.services;

import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.models.User;

import java.util.List;

public interface UserService {

    User findByUsername(String username);

    List<User> findAll();

    User findById(Long id);

    void save(User user);

    void update(User user);

    void delete(Long id);

}

