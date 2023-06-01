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
     * - Врубил перенаправление урла "/user/page" на страницу "user.html";
     * - Врубил перенаправление урла "/admin/page" на страницу "admin.html";
     * - Врубил перенаправление урла "/" на главную страницу сайта "index.html";
     * - Оставил под требование таски 3.1.4 перенаправление урла "/login" на свою кастомную страницу
     * "login.html"с требуемым в той задаче оформлением.
     */
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin/page").setViewName("admin/admin");
        registry.addViewController("/user/page").setViewName("user/user");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
    }

}
