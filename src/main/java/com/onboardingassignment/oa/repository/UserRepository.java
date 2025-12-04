package com.onboardingassignment.oa.repository;

import com.onboardingassignment.oa.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}
