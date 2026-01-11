package ru.kata.spring.boot_security.demo.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.dao.RoleDao;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleDao roleDao;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleDao roleDao, UserService userService, PasswordEncoder passwordEncoder) {
        this.roleDao = roleDao;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Role adminRole = roleDao.findByName("ROLE_ADMIN").orElseGet(() -> roleDao.save(new Role("ROLE_ADMIN")));
        Role userRole  = roleDao.findByName("ROLE_USER").orElseGet(() -> roleDao.save(new Role("ROLE_USER")));

        // admin
        userService.findByUsername("admin").orElseGet(() -> {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setName("Admin");
            admin.setSurname("Root");
            admin.setAge(30);
            admin.setRoles(Set.of(adminRole, userRole)); // можно обе роли
            userService.create(admin);
            return admin;
        });

        // user
        userService.findByUsername("user").orElseGet(() -> {
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user"));
            user.setName("User");
            user.setSurname("Simple");
            user.setAge(25);
            user.setRoles(Set.of(userRole));
            userService.create(user);
            return user;
        });
    }
}
