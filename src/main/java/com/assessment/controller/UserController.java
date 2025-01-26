package com.assessment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.assessment.entity.User;
import com.assessment.service.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/load-users")
    public String loadUsers() {
        userService.loadUsers();
        return "Users data loaded into in-memory H2 database successfully!";
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String role) {
        return ResponseEntity.ok(userService.getUsersByRole(role));
  
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<User>> getUsersSortedByAge(@RequestParam boolean ascending) {
        return ResponseEntity.ok(userService.getUsersSortedByAge(ascending));
    }

    @GetMapping("/user/{idOrSsn}")
    public ResponseEntity<User> getUserByIdOrSsn(@PathVariable String idOrSsn) {
        try {
            Long id = Long.parseLong(idOrSsn);
            return ResponseEntity.ok(userService.getUserByIdOrSsn(id, null));
        } catch (NumberFormatException e) {
            return ResponseEntity.ok(userService.getUserByIdOrSsn(null, idOrSsn));
        }
    }

}
