package org.dreamteam.sda.controller.web;

import org.dreamteam.sda.model.User;
import org.dreamteam.sda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String manageUsers(Model model) {
        model.addAttribute("users", userService.getUsers()); // Zoznam používateľov
        model.addAttribute("user", new User()); // Prázdny objekt pre formulár
        return "users"; // Šablóna users.html
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user, @RequestParam String role) {
        userService.createUser(user.getUsername(), user.getPassword(), role);
        return "redirect:/admin/users/";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users/";
    }
    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id); // Metóda na získanie používateľa podľa ID
        model.addAttribute("user", user);
        return "user-edit"; // Názov šablóny
    }

    // Spracovanie zmien v používateľovi
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user) {
        userService.updateUser(id, user); // Metóda na aktualizáciu používateľa
        return "redirect:/admin/users/";
    }

    // REST API
    @ResponseBody
    @GetMapping("/api")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @ResponseBody
    @PostMapping("/api")
    public User createApiUser(@RequestParam String name, @RequestParam String password, @RequestParam String role) {
        return userService.createUser(name, password, role);
    }

    @ResponseBody
    @DeleteMapping("/api/{id}")
    public void deleteApiUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}