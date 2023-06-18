package ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.utils.InitiateUtils;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 1, max = 25, message = "Введите имя(логин) длиною от 1 до 25 символов!")
    private String username;
    @NotNull
    @Size(min = 1, max = 25, message = "Введите фамилию длиною от 1 до 25 символов!")
    private String surname;
    @Min(value = 1, message = "Введите значение возраста от 1 до 110!")
    @Max(value = 110, message = "Введите значение возраста от 1 до 110!")
    private Byte age;
    @Email(message = "Введите правильный адрес почты!")
    private String email;
    @NotEmpty(message = "Введите пароль!")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

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

}
