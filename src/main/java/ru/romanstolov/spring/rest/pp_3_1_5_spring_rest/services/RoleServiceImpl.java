package ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.models.Role;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.repositories.RoleRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Collection<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Collection<Role> createCollectionRoles(String[] roles) {
        Collection<Role> rolesCollection = new HashSet<>();
        for (String role : roles) {
            Optional<Role> optionalRole = roleRepository.findByName(role);
            optionalRole.ifPresent(rolesCollection::add);
        }
        return rolesCollection;
    }

}
