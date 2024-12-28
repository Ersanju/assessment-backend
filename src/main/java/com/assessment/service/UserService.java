package com.assessment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.assessment.dto.UserResponse;
import com.assessment.entity.User;
import com.assessment.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void loadUsers() {
        
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://dummyjson.com/users";
        UserResponse response = restTemplate.getForObject(url, UserResponse.class);

        if (response != null) {
            userRepository.saveAll(response.getUsers());
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }
    
}
