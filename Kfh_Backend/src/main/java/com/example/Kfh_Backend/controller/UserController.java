package com.example.Kfh_Backend.controller;

import ch.qos.logback.core.model.Model;
import com.example.Kfh_Backend.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // Sign in feature
    @GetMapping("/signin")
    public String signIn() {
        // Sign in logic
        return "signin";
    }

    // Add user with email and role attributes
    @PostMapping("/addUser")
    public String addUser(@RequestParam String email, @RequestParam String role) {
        User user = new User(email, role);
        userService.addUser(user);
        return "redirect:/users";
    }

    // Edit role: Select a user from a list of users and change their role
    @GetMapping("/editRole")
    public String editRole(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "editRole";
    }

    @PostMapping("/changeRole")
    public String changeRole(@RequestParam long userId, @RequestParam String newRole) {
        userService.changeUserRole(userId, newRole);
        return "redirect:/users";
    }

    // Remove user: Remove user from database
    @PostMapping("/removeUser")
    public String removeUser(@RequestParam long userId) {
        userService.removeUser(userId);
        return "redirect:/users";
    }
}
