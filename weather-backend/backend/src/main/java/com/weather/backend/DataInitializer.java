package com.weather.backend;

import com.weather.backend.domain.Role;
import com.weather.backend.domain.User;
import com.weather.backend.repo.RoleRepository;
import com.weather.backend.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            Role userRole = roleRepository.save(Role.builder().name("USER").build());
            Role adminRole = roleRepository.save(Role.builder().name("ADMIN").build());

            User user = User.builder()
                    .email("user@klu.edu")
                    .password(passwordEncoder.encode("password"))
                    .roles(Set.of(userRole))
                    .build();
            userRepository.save(user);

            User admin = User.builder()
                    .email("admin@klu.edu")
                    .password(passwordEncoder.encode("admin"))
                    .roles(Set.of(adminRole))
                    .build();
            userRepository.save(admin);
        }
    }
}
