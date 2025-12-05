package com.onboardingassignment.oa.repository.user;

import com.onboardingassignment.oa.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserCrudRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}
