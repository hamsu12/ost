package com.example.ost.controller;

import com.example.ost.domain.user.User;
import com.example.ost.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public User login(
            @RequestParam String spotifyId,
            @RequestParam String name,
            @RequestParam(required = false) String image
    ) {
        return service.registerOrLogin(spotifyId, name, image);
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam Long userId) {
        service.deleteUser(userId);
        return "deleted";
    }

}
