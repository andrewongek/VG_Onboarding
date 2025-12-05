package com.onboardingassignment.oa.services;

import com.onboardingassignment.oa.repository.user.UserCrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DbUserDetailsService implements UserDetailsService {

    private final UserCrudRepository userCrudRepository;

    private final PasswordEncoder passwordEncoder;

    public DbUserDetailsService(UserCrudRepository userCrudRepository,
                                PasswordEncoder passwordEncoder) {
        this.userCrudRepository = userCrudRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.onboardingassignment.oa.entities.User user =
                userCrudRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword()) // MUST be BCrypt encoded!
                .roles(user.getRole())  // e.g. "ADMIN"
                .build();
    }
}
