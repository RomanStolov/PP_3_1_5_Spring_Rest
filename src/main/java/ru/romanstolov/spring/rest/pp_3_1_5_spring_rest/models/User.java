package ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.utils.InitiateUtils;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * МОИ ДЕЙСТВИЯ:
 * <p>
 * - Добавил имплементацию "UserDetails" и реализовал в классе все его СЕМЬ методов.
 * <p>
 * - Реализовал связь между таблицей пользователей "users" и таблицей ролей "roles" как "многие-ко-многим".
 * Причём в этой задаче переделал эту связь из двунаправленной в одностороннюю "User->Role". Связующая третья таблица
 * "users_roles" создаётся автоматически.
 * <p>
 * - Добавил каскад "cascade = {CascadeType.PERSIST, CascadeType.MERGE}" над полем со списком ролей. "CascadeType" -
 * этот параметр определяет, что должно происходить с зависимыми сущностями, если мы меняем главную сущность.
 * В JPA спецификации есть такие значения этого параметра:
 * .    - ALL - все действия, которые мы выполняем с родительским объектом, нужно повторить и для его зависимых
 * .            объектов.
 * .    - PERSIST - если мы сохраняем в базу родительский объект, то это же нужно сделать и с его зависимыми
 * .            объектами.
 * .    - MERGE - если мы обновляем в базе родительский объект, то это же нужно сделать и с его зависимыми объектами.
 * .    - REMOVE (DELETE) - если мы удаляем в базе родительский объект, то это же нужно сделать и с его зависимыми
 * .            объектами.
 * .    - REFRESH (SAVE_UPDATE) - дублируют действия, которые выполняются с родительским объектом к его зависимому
 * .            объекту.
 * .    - DETACH - если мы удаляем родительский объект из сессии, то это же нужно сделать и с его зависимыми объектами.
 * Однако Hibernate расширяет эту спецификацию еще на три варианта:
 * .    - REPLICATE
 * .    - SAVE_UPDATE
 * .    - LOCK
 * <p>
 * - Указал "ленивую" загрузку "fetch = FetchType.LAZY" над полем со списком ролей. Параметр fetch позволяет управлять
 * режимами загрузки зависимых объектов. Обычно он принимает одно из двух значений: FetchType.LAZY или FetchType.EAGER.
 * <p>
 * - Убрал валидацию данных над полями "на стороне сервера" в этой задаче по сравнению с прошлой. Так быстрее для
 * тестирования;
 * <p>
 */
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String surname;
    private Byte age;
    private String email;
    private String password;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles = new HashSet<>();

    public User() {

    }

    /**
     * Конструктор необходимый лишь для того чтоб изначально заполнить БД с использованием утилитного класса
     * "InitiateUtils implements CommandLineRunner"
     *
     * @see InitiateUtils
     */
    public User(String username, String surname, Byte age, String email, String password, Collection<Role> roles) {
        this.username = username;
        this.surname = surname;
        this.age = age;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    /**
     * Метод добавления роли пользователю.
     */
    public void addRole(Role role) {
        this.roles.add(role);
    }

    /**
     * Метод удаления роли у пользователя.
     */
    public void removeRole(Role role) {
        this.roles.remove(role);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * В этом переопределённом методе прописал возврат authorities пользователя в виде List<SimpleGrantedAuthority>
     * представляющего собою ArrayList<SimpleGrantedAuthority> сделанный из Set<Role> текущего пользователя.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    /**
     * В этом переопределённом методе прописал "true"
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Переименовал у себя в классе "name" в "username"
     * В этом переопределённом методе прописал возврат "username"
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * В этом переопределённом методе прописал "true"
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * В этом переопределённом методе прописал "true"
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * В этом переопределённом методе прописал "true"
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * В этом переопределённом методе прописал "true"
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username) && surname.equals(user.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, surname);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }

}
