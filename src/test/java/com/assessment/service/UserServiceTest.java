package com.assessment.service;

import com.assessment.entity.User;
import com.assessment.repository.UserRepository;
import com.assessment.dto.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Sort;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserService userService;

    private User user1;
    private User user2;
    private List<User> userList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user1 = new User();
        user2 = new User();
        userList = Arrays.asList(user1, user2);
    }

    @Test
    void testLoadUsers() {
        String url = "https://dummyjson.com/users";
        UserResponse mockResponse = new UserResponse();
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("John");
        user1.setLastName("Doe");

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Jane");
        user2.setLastName("Smith");

        mockResponse.setUsers(List.of(user1, user2));

        when(restTemplate.getForObject(url, UserResponse.class)).thenReturn(mockResponse);

        userService.loadUsers();

    }

    @Test
    void testLoadUsers_NullResponse() {
        String url = "https://dummyjson.com/users";
        when(restTemplate.getForObject(url, UserResponse.class)).thenReturn(null);

        userService.loadUsers();
    }

    @Test
    void testGetAllUsers() {

        when(userRepository.findAll()).thenReturn(userList);
        List<User> users = userService.getAllUsers();
        assertNotNull(users);
        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUsersByRole() {
        String role = "admin";
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setRole("admin");

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setRole("admin");

        List<User> mockUsers = List.of(user1, user2);

        when(userRepository.findByRole(role)).thenReturn(mockUsers);

        List<User> result = userService.getUsersByRole(role);

        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
        verify(userRepository, times(1)).findByRole(role);
    }

    @Test
    void testGetUsersSortedByAge() {

        when(userRepository.findAll(Sort.by("age").ascending())).thenReturn(Arrays.asList(user2, user1));
        when(userRepository.findAll(Sort.by("age").descending())).thenReturn(Arrays.asList(user1, user2));

        List<User> usersAsc = userService.getUsersSortedByAge(true);
        List<User> usersDesc = userService.getUsersSortedByAge(false);

        assertEquals(2, usersAsc.size());
        assertEquals(user2, usersAsc.get(0));
        assertEquals(user1, usersAsc.get(1));

        assertEquals(2, usersDesc.size());
        assertEquals(user1, usersDesc.get(0));
        assertEquals(user2, usersDesc.get(1));

        verify(userRepository, times(1)).findAll(Sort.by("age").ascending());
        verify(userRepository, times(1)).findAll(Sort.by("age").descending());
    }

    @Test
    void testGetUserByIdOrSsn_NoMatch() {

        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        when(userRepository.findBySsn("123-45-6789")).thenReturn(Optional.empty());

        User userById = userService.getUserByIdOrSsn(1L, null);
        User userBySsn = userService.getUserByIdOrSsn(null, "123-45-6789");

        assertNull(userById);
        assertNull(userBySsn);
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findBySsn("123-45-6789");
    }
}
