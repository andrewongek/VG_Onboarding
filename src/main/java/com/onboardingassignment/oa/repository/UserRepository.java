package com.onboardingassignment.oa.repository;

import com.onboardingassignment.oa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
