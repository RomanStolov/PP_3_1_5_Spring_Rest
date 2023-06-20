package ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.services;

import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.models.Role;

import java.util.Collection;

public interface RoleService {

    Collection<Role> findAll();

    Collection<Role> createCollectionRoles(String[] roles);

}
