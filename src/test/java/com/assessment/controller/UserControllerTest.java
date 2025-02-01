package com.assessment.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.assessment.entity.User;
import com.assessment.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private User user1, user2;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        
        // Creating test users
        user1 = new User();
        user1.setId(1L);
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setRole("admin");
        user1.setAge(25);
        user1.setSsn("804-492-390");

        user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setRole("user");
        user2.setAge(30);
        user2.setSsn("904-293-839");
    }

    // Test Case 1: Load Users
    @Test
    void testLoadUsers() throws Exception {
        mockMvc.perform(get("/api/load-users"))
                .andExpect(status().isOk())
                .andExpect(content().string("Users data loaded into in-memory H2 database successfully!"));

        verify(userService, times(1)).loadUsers();
    }

    // Test Case 2: Get All Users
    @Test
    void testGetAllUsers() throws Exception {
        List<User> users = Arrays.asList(user1, user2);
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/all-users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(userService, times(1)).getAllUsers();
    }

    // Test Case 3: Get Users by Role
    @Test
    void testGetUsersByRole() throws Exception {
        List<User> users = List.of(user1);
        when(userService.getUsersByRole("admin")).thenReturn(users);

        mockMvc.perform(get("/api/role/admin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].role").value("admin"));

        verify(userService, times(1)).getUsersByRole("admin");
    }

    // Test Case 4: Get Users Sorted by Age
    @Test
    void testGetUsersSortedByAge() throws Exception {
        List<User> users = Arrays.asList(user1, user2);
        when(userService.getUsersSortedByAge(true)).thenReturn(users);

        mockMvc.perform(get("/api/sorted").param("ascending", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].age").value(25));

        verify(userService, times(1)).getUsersSortedByAge(true);
    }

    // Test Case 5: Get User by ID
    @Test
    void testGetUserByIdOrSsn_withId() throws Exception {
        when(userService.getUserByIdOrSsn(1L, null)).thenReturn(user1);

        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"));

        verify(userService, times(1)).getUserByIdOrSsn(1L, null);
    }

    // Test Case 6: Get User by SSN
    @Test
    void testGetUserByIdOrSsn_withSsn() throws Exception {
        when(userService.getUserByIdOrSsn(null, "804-492-390")).thenReturn(user1);

        mockMvc.perform(get("/api/user/804-492-390"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ssn").value("804-492-390"))
                .andExpect(jsonPath("$.firstName").value("John"));

        verify(userService, times(1)).getUserByIdOrSsn(null, "804-492-390");
    }
}
