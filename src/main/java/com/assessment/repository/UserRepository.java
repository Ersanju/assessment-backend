package com.assessment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.assessment.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByRole(String role);
    Optional<User> findBySsn(String ssn);
}
