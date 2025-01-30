package com.assessment.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.web.client.RestTemplate;

import com.assessment.dto.UserResponse;
import com.assessment.entity.User;
import com.assessment.exception.UserNotFoundException;
import com.assessment.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserService userService;

    private User user1, user2;
    private UserResponse mockResponse;

    @Captor
    private ArgumentCaptor<List<User>> userListCaptor;

    @BeforeEach
    void setUp() {
        // Creating test users
        user1 = new User();
        user1.setId(1L);
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setRole("admin");
        user1.setAge(30);
        user1.setSsn("804-492-390");

        user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setRole("user");
        user2.setAge(25);
        user2.setSsn("904-293-839");

        mockResponse = new UserResponse();
        mockResponse.setUsers(List.of(user1, user2));
    }

    // Test Case 1: Load users
    @Test
    void testLoadUsers_Success() {
        userService.loadUsers();
    }

    //Test Case 2: Fetch all users
    @Test
    void testGetAllUsers_ReturnsListOfUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    //Test Case 3: Fetch users by role - Role exists
    @Test
    void testGetUsersByRole_ValidRole_ReturnsUsers() {
        when(userRepository.findByRole("admin")).thenReturn(List.of(user1));

        List<User> result = userService.getUsersByRole("admin");

        assertEquals(1, result.size());
        assertEquals("admin", result.get(0).getRole());
        verify(userRepository, times(1)).findByRole("admin");
    }

    //Test Case 4: Fetch users by role - Role not found
    @Test
    void testGetUsersByRole_InvalidRole_ThrowsException() {
        when(userRepository.findByRole("invalid_role")).thenReturn(List.of());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, 
            () -> userService.getUsersByRole("invalid_role"));

        assertEquals("No user found for the role: invalid_role", exception.getMessage());
        verify(userRepository, times(1)).findByRole("invalid_role");
    }

    //Test Case 5: Fetch users sorted by age - Ascending
    @Test
    void testGetUsersSortedByAge_AscendingOrder_ReturnsSortedUsers() {
        when(userRepository.findAll(Sort.by("age").ascending())).thenReturn(List.of(user2, user1));

        List<User> result = userService.getUsersSortedByAge(true);

        assertEquals(25, result.get(0).getAge());
        assertEquals(30, result.get(1).getAge());
        verify(userRepository, times(1)).findAll(Sort.by("age").ascending());
    }

    //Test Case 6: Fetch users sorted by age - Descending
    @Test
    void testGetUsersSortedByAge_DescendingOrder_ReturnsSortedUsers() {
        when(userRepository.findAll(Sort.by("age").descending())).thenReturn(List.of(user1, user2));

        List<User> result = userService.getUsersSortedByAge(false);

        assertEquals(30, result.get(0).getAge());
        assertEquals(25, result.get(1).getAge());
        verify(userRepository, times(1)).findAll(Sort.by("age").descending());
    }

    //Test Case 7: Fetch user by ID - Valid ID
    @Test
    void testGetUserByIdOrSsn_ValidId_ReturnsUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        User result = userService.getUserByIdOrSsn(1L, null);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userRepository, times(1)).findById(1L);
    }

    //Test Case 8: Fetch user by SSN - Valid SSN
    @Test
    void testGetUserByIdOrSsn_ValidSsn_ReturnsUser() {
        when(userRepository.findBySsn("804-492-390")).thenReturn(Optional.of(user1));

        User result = userService.getUserByIdOrSsn(null, "804-492-390");

        assertNotNull(result);
        assertEquals("804-492-390", result.getSsn());
        verify(userRepository, times(1)).findBySsn("804-492-390");
    }

    //Test Case 9: Fetch user by ID - User not found
    @Test
    void testGetUserByIdOrSsn_InvalidId_ThrowsException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, 
            () -> userService.getUserByIdOrSsn(99L, null));

        assertEquals("User not found with Id: 99", exception.getMessage());
        verify(userRepository, times(1)).findById(99L);
    }

    //Test Case 10: Fetch user by SSN - User not found
    @Test
    void testGetUserByIdOrSsn_InvalidSsn_ThrowsException() {
        when(userRepository.findBySsn("999-99-9999")).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, 
            () -> userService.getUserByIdOrSsn(null, "999-99-9999"));

        assertEquals("User not found with Ssn: 999-99-9999", exception.getMessage());
        verify(userRepository, times(1)).findBySsn("999-99-9999");
    }
}
