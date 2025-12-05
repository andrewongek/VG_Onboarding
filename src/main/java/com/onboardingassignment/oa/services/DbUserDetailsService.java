package com.onboardingassignment.oa.services;

import com.onboardingassignment.oa.repository.UserRepository;
import com.onboardingassignment.oa.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DbUserDetailsService implements UserDetailsService {

    private final UserRepository userCrudRepository;

    private final PasswordEncoder passwordEncoder;

    public DbUserDetailsService(UserRepository userCrudRepository,
                                PasswordEncoder passwordEncoder) {
        this.userCrudRepository = userCrudRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.onboardingassignment.oa.model.User user =
                userCrudRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new CustomUserDetails(user);
    }
}
