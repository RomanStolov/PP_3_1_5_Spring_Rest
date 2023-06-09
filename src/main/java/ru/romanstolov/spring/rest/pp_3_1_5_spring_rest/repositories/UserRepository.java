package ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("Select u from User u left join fetch u.roles where u.username=:username")
    Optional<User> findByUsername(String username);

    @Query("Select distinct u from User u left join fetch u.roles")
    List<User> findAllWithoutNPlusOneProblem();

}
