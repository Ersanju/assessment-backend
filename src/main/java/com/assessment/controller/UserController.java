package com.assessment.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/load-users")
    @Operation(summary = "Load users data", description = "Load users data into in-memory H2 database")
    public String loadUsers() {
        logger.info("Request to load users data into in-memory H2 database");
        userService.loadUsers();
        return "Users data loaded into in-memory H2 database successfully!";
    }

    @GetMapping("/all-users")
    @Operation(summary = "Get all users", description = "Get all users from in-memory H2 database")
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Request to get all users from in-memory H2 database");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/role/{role}")
    @Operation(summary = "Get users by role", description = "Get users by role from in-memory H2 database")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String role) {
        logger.info("Request to get users by role from in-memory H2 database");
        return ResponseEntity.ok(userService.getUsersByRole(role));
  
    }

    @GetMapping("/sorted")
    @Operation(summary = "Get users sorted by age", description = "Get users sorted by age from in-memory H2 database")
    public ResponseEntity<List<User>> getUsersSortedByAge(@RequestParam boolean ascending) {
        logger.info("Request to get users sorted by age from in-memory H2 database");
        return ResponseEntity.ok(userService.getUsersSortedByAge(ascending));
    }

    @GetMapping("/user/{idOrSsn}")
    @Operation(summary = "Get user by ID or SSN", description = "Get user by ID or SSN from in-memory H2 database")
    public ResponseEntity<User> getUserByIdOrSsn(@PathVariable String idOrSsn) {
        logger.info("Request to get user by ID or SSN from in-memory H2 database");
        try {
            logger.info(idOrSsn + " is a number, trying to get user by ID");
            Long id = Long.parseLong(idOrSsn);
            return ResponseEntity.ok(userService.getUserByIdOrSsn(id, null));
        } catch (NumberFormatException e) {
            logger.info(idOrSsn + " is not a number, trying to get user by SSN");
            return ResponseEntity.ok(userService.getUserByIdOrSsn(null, idOrSsn));
        }
    }

}
