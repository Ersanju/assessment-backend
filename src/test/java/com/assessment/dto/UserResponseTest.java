package com.assessment.dto;

import static org.junit.jupiter.api.Assertions.*;
import com.assessment.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class UserResponseTest {

    private UserResponse userResponse;

    @BeforeEach
    void setUp() {

        // Initialize UserResponse before each test
        userResponse = new UserResponse();
    }

    @Test
    void testGettersAndSetters() {

        // Create a list of User objects
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("John");
        user1.setLastName("Doe");

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Jane");
        user2.setLastName("Doe");

        users.add(user1);
        users.add(user2);

        userResponse.setUsers(users);

        assertNotNull(userResponse.getUsers());
        assertEquals(2, userResponse.getUsers().size());

        // Validate the first user
        assertEquals(1L, userResponse.getUsers().get(0).getId());
        assertEquals("John", userResponse.getUsers().get(0).getFirstName());
        assertEquals("Doe", userResponse.getUsers().get(0).getLastName());

        // Validate the second user
        assertEquals(2L, userResponse.getUsers().get(1).getId());
        assertEquals("Jane", userResponse.getUsers().get(1).getFirstName());
        assertEquals("Doe", userResponse.getUsers().get(1).getLastName());
    }

    @Test
    void testEmptyUsersList() {
        List<User> users = new ArrayList<>();
        userResponse.setUsers(users);

        assertNotNull(userResponse.getUsers());
        assertTrue(userResponse.getUsers().isEmpty());
    }

    @Test
    void testNullUsersList() {
        userResponse.setUsers(null);

        assertNull(userResponse.getUsers());
    }
}
