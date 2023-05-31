package ru.romanstolov.spring.rest.pp_3_1_5_spring_rest.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * ИСХОДНЫЕ ДАННЫЕ В ЗАДАЧЕ !!!
     * **********************************************************************************
     * public void addViewControllers(ViewControllerRegistry registry) {
     * registry.addViewController("/user").setViewName("user");
     * }
     * **********************************************************************************
     * <p>
     * МОИ ДЕЙСТВИЯ:
     * - Врубил перенаправление урла "/user" на вьюху "user.html"
     * - Врубил перенаправление урла "/admin/users" на вьюху "admin.html"
     * - Врубил перенаправление урла "/user" на вьюху "index.html"
     * - Врубил под требование таски 3.1.4 перенаправление урла "/login" на свою кастомную вьюху с
     * требуемым оформлением "login.html"
     */
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/admin/users").setViewName("admin/admin");
        registry.addViewController("/admin/page").setViewName("admin/admin");
//        registry.addViewController("/user").setViewName("user/user");
        registry.addViewController("/user/page").setViewName("user/user");

        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
    }

}
