package com.weather.backend.controller;

import com.weather.backend.domain.User;
import com.weather.backend.domain.Role;
import com.weather.backend.domain.UserPreferences;
import com.weather.backend.repo.UserRepository;
import com.weather.backend.repo.RoleRepository;
import com.weather.backend.repo.UserPreferencesRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class AdminController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserPreferencesRepository userPreferencesRepository;

    @Autowired
    private WeatherController weatherController;

    public AdminController(UserRepository userRepository, RoleRepository roleRepository, UserPreferencesRepository userPreferencesRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userPreferencesRepository = userPreferencesRepository;
    }

    @GetMapping("/api/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminDashboard() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return ResponseEntity.ok("Welcome to the Admin Dashboard, " + username);
    }

    @GetMapping("/api/admin/user-preferences/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserPreferences(@PathVariable String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }
        User user = userOpt.get();
        Optional<UserPreferences> prefsOpt = userPreferencesRepository.findByUser(user);
        if (prefsOpt.isEmpty()) {
            // Return default preferences if none found
            UserPreferences defaultPrefs = new UserPreferences();
            defaultPrefs.setUser(user);
            return ResponseEntity.ok(defaultPrefs);
        }
        return ResponseEntity.ok(prefsOpt.get());
    }

    @PostMapping("/api/admin/promote")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> promoteUserToAdmin(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        System.out.println("Promote request received for email: " + email);
        try {
            User user = userRepository.findByEmail(email).orElse(null);
            if (user == null) {
                System.out.println("User not found for email: " + email);
                return ResponseEntity.status(404).body("User not found");
            }
            Role adminRole = roleRepository.findByName("ADMIN").orElseThrow(() -> new RuntimeException("ADMIN role not found"));
            user.getRoles().add(adminRole);
            userRepository.save(user);
            System.out.println("User promoted successfully: " + email);
            return ResponseEntity.ok("User promoted to admin successfully");
        } catch (Exception e) {
            System.out.println("Error promoting user: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }

    @PostMapping("/api/admin/demote")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> demoteUserFromAdmin(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        System.out.println("Demote request received for email: " + email);
        try {
            User user = userRepository.findByEmail(email).orElse(null);
            if (user == null) {
                System.out.println("User not found for email: " + email);
                return ResponseEntity.status(404).body("User not found");
            }
            Role adminRole = roleRepository.findByName("ADMIN").orElseThrow(() -> new RuntimeException("ADMIN role not found"));
            if (user.getRoles().contains(adminRole)) {
                user.getRoles().remove(adminRole);
                userRepository.save(user);
                System.out.println("User demoted successfully: " + email);
                return ResponseEntity.ok("User demoted from admin successfully");
            } else {
                return ResponseEntity.status(400).body("User is not an admin");
            }
        } catch (Exception e) {
            System.out.println("Error demoting user: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }

    @PostMapping("/api/admin/user-preferences")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUserPreferences(@RequestBody UserPreferences preferences) {
        try {
            Optional<User> userOpt = userRepository.findById(preferences.getUser().getId());
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(404).body("User not found");
            }
            preferences.setUser(userOpt.get());
            UserPreferences savedPrefs = userPreferencesRepository.save(preferences);
            return ResponseEntity.ok(savedPrefs);
        } catch (Exception e) {
            System.out.println("Error updating user preferences: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }

    @GetMapping("/api/admin/city-usage")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCityUsageStats() {
        try {
            Map<String, AtomicInteger> usageMap = weatherController.getCityUsageMap();
            Map<String, Integer> stats = new HashMap<>();
            for (Map.Entry<String, AtomicInteger> entry : usageMap.entrySet()) {
                stats.put(entry.getKey(), entry.getValue().get());
            }
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            System.out.println("Error fetching city usage stats: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }
}
