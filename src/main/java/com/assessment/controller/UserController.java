package com.assessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/load-users")
    public String loadUsers() {
        userService.loadUsers();
        return "Users data loaded into in-memory H2 database successfully!";
    }
}
