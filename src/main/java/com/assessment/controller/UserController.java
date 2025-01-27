package com.assessment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.assessment.entity.User;
import com.assessment.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "ExampleController", description = "Sample API for demonstrating Swagger")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/load-users")
    @Operation(summary = "Load users data", description = "Load users data into in-memory H2 database")
    public String loadUsers() {
        userService.loadUsers();
        return "Users data loaded into in-memory H2 database successfully!";
    }

    @GetMapping("/all-users")
    @Operation(summary = "Get all users", description = "Get all users from in-memory H2 database")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/role/{role}")
    @Operation(summary = "Get users by role", description = "Get users by role from in-memory H2 database")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String role) {
        return ResponseEntity.ok(userService.getUsersByRole(role));
  
    }

    @GetMapping("/sorted")
    @Operation(summary = "Get users sorted by age", description = "Get users sorted by age from in-memory H2 database")
    public ResponseEntity<List<User>> getUsersSortedByAge(@RequestParam boolean ascending) {
        return ResponseEntity.ok(userService.getUsersSortedByAge(ascending));
    }

    @GetMapping("/user/{idOrSsn}")
    @Operation(summary = "Get user by ID or SSN", description = "Get user by ID or SSN from in-memory H2 database")
    public ResponseEntity<User> getUserByIdOrSsn(@PathVariable String idOrSsn) {
        try {
            Long id = Long.parseLong(idOrSsn);
            return ResponseEntity.ok(userService.getUserByIdOrSsn(id, null));
        } catch (NumberFormatException e) {
            return ResponseEntity.ok(userService.getUserByIdOrSsn(null, idOrSsn));
        }
    }

}
