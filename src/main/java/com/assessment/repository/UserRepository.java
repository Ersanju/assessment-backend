package com.assessment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.assessment.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByRole(String role);
}
