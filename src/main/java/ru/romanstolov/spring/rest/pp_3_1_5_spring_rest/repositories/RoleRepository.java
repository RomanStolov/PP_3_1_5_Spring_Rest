package ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.models.Role;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.services.UserServiceImpl;

import java.util.Optional;

/**
 * В задаче 3.1.5 добавил в этот репозиторий поиск роли по имени для использования в классе UserServiceImpl
 * в методе "public Collection<Role> createCollectionRoles(String[] roles)"
 *
 * @see UserServiceImpl#createCollectionRoles
 * <p>
 * Тут можно самому писать любые методы задавая параметры метода и тип возвращаемого значения при совершенно
 * произвольном наименовании самого метода!
 * Этот интерфейс расширяет интерфейс JpaRepository из Spring Data JPA. JpaRepository определяет стандартные
 * методы CRUD, а также операции, специфичные для JPA. Нам не нужно писать код реализации, потому что Spring
 * Data JPA сгенерирует необходимый код во время выполнения в виде прокси-экземпляров.
 * Цель написания интерфейса репозитория - сообщить Spring Data JPA о типе домена (Role) и типе идентификатора
 * (Long) для работы.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

}
