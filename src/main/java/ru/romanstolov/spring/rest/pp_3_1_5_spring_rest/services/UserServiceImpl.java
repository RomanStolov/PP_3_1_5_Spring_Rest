package ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.models.Role;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.models.User;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.repositories.RoleRepository;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.repositories.UserRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public Collection<Role> getListRole() {
        return roleRepository.findAll();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void update(User user) {
        if (!user.getPassword().equals(findUserById(user.getId()).getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User deleteUser = new User();
        deleteUser.setId(id);
        userRepository.delete(deleteUser);
    }

    /**
     * Добавил в таске 3.1.5
     * Возвращает коллекцию ролей согласно запроса из метода сохранения нового пользователя в рест-контроллере
     */
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
