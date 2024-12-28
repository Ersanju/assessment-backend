package com.assessment.dto;

import java.util.List;
import com.assessment.entity.User;

public class UserResponse {
    
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
