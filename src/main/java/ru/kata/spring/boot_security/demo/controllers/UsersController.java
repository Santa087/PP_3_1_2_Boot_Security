package ru.kata.spring.boot_security.demo.controllers;

import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model) {
        // получим всех юзеров из ДАО и передадим на отображение в представление
        model.addAttribute("users", userService.getAll());
        return "admin/index";
    }

    @GetMapping("/show")
    public String show(@RequestParam("id") Long id, Model model) {
        // получим одного юзера по его id из ДАО и передадим на отображение в представление
        model.addAttribute("user", userService.getById(id));
        return "admin/show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute ("user") User user) {
        // форма создания нового юзера
        return "admin/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("user") User user) {
        userService.create(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "admin/edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}
