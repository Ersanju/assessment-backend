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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testLoadUsers() throws Exception {
        mockMvc.perform(get("/api/load-users"))
                .andExpect(status().isOk())
                .andExpect(content().string("Users data loaded into in-memory H2 database successfully!"));

        verify(userService, times(1)).loadUsers();
    }

    @Test
    void testGetAllUsers() throws Exception {
        // Mock the service response
        List<User> users = Arrays.asList(
                new User() {{
                    setId(1L);
                    setFirstName("John");
                }},
                new User() {{
                    setId(2L);
                    setFirstName("Jane");
                }}
        );

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/all-users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].firstName").value("Jane"));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUsersByRole() throws Exception {
        // Mock the service response
        List<User> users = Arrays.asList(
                new User() {{
                    setId(1L);
                    setRole("Admin");
                }},
                new User() {{
                    setId(2L);
                    setRole("Admin");
                }}
        );

        when(userService.getUsersByRole("Admin")).thenReturn(users);

        mockMvc.perform(get("/api/role/Admin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].role").value("Admin"));

        verify(userService, times(1)).getUsersByRole("Admin");
    }

    @Test
    void testGetUsersSortedByAge() throws Exception {
        // Mock the service response
        List<User> users = Arrays.asList(
                new User() {{
                    setId(1L);
                    setAge(25);
                }},
                new User() {{
                    setId(2L);
                    setAge(30);
                }}
        );

        when(userService.getUsersSortedByAge(true)).thenReturn(users);

        mockMvc.perform(get("/api/sorted").param("ascending", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].age").value(25))
                .andExpect(jsonPath("$[1].age").value(30));

        verify(userService, times(1)).getUsersSortedByAge(true);
    }

    @Test
    void testGetUserByIdOrSsn_withId() throws Exception {
        // Mock the service response for ID
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");

        when(userService.getUserByIdOrSsn(1L, null)).thenReturn(user);

        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"));

        verify(userService, times(1)).getUserByIdOrSsn(1L, null);
    }

    @Test
    void testGetUserByIdOrSsn_withSsn() throws Exception {
        // Mock the service response for SSN
        User user = new User();
        user.setSsn("123-45-6789");
        user.setFirstName("Jane");

        when(userService.getUserByIdOrSsn(null, "123-45-6789")).thenReturn(user);

        mockMvc.perform(get("/api/user/123-45-6789"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ssn").value("123-45-6789"))
                .andExpect(jsonPath("$.firstName").value("Jane"));

        verify(userService, times(1)).getUserByIdOrSsn(null, "123-45-6789");
    }
}
