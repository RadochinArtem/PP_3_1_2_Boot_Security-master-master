package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping
    public String getAllUsersPage(Principal principal, Model model) {
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("logUser", userService.findUserByEmail(principal.getName()));
        model.addAttribute("roles", roleService.findAllRoles());
        return "admin/all";
    }

    @GetMapping("/new")
    public String getCreateUserPage(Principal principal, Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("logUser", userService.findUserByEmail(principal.getName()));
        model.addAttribute("roles" , roleService.findAllRoles());
        return "admin/new";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
    @PutMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user,@PathVariable("id") Long id) {
        userService.updateUser(user,id);
        return "redirect:/admin";
    }
}