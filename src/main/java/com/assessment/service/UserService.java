package com.assessment.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.assessment.dto.UserResponse;
import com.assessment.entity.User;
import com.assessment.exception.UserNotFoundException;
import com.assessment.repository.UserRepository;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void loadUsers() {
        logger.info("Loading users data into in-memory H2 database");
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://dummyjson.com/users";

        try {
            UserResponse response = restTemplate.getForObject(url, UserResponse.class);
            if (response != null) {
                userRepository.saveAll(response.getUsers());
                logger.info("Successfully saved {} users to database", response.getUsers().size());
            }
        } catch(Exception e) {
            logger.error("Error occurred while loading users data: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch user from external API");
        }
    }

    public List<User> getAllUsers() {
        logger.debug("Fetching all users from in-memory H2 database");
        return userRepository.findAll();
    }
    
    public List<User> getUsersByRole(String role) {
        logger.debug("Fetching users by role {}", role);
        List<User> users = userRepository.findByRole(role);
        if(users.isEmpty()){
            throw new UserNotFoundException("No user found for the role: " + role);
        }
        return users;
    }

    public List<User> getUsersSortedByAge(boolean ascending) {
        logger.debug("Fetching users sorted by age in {} order", ascending ? "ascending" : "descending");
        Sort sort = ascending ? Sort.by("age").ascending() : Sort.by("age").descending();
        return userRepository.findAll(sort);
    }
    
    public User getUserByIdOrSsn(Long id, String ssn) {
        logger.debug("Fetching user by ID or SSN");
        return id != null ? userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found with Id: " + id)) 
                          : userRepository.findBySsn(ssn).orElseThrow(()-> new UserNotFoundException("User not found with Ssn: " + ssn));
    }
}
