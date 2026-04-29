package com.api.productengine.controller;

import com.api.productengine.model.User;
import com.api.productengine.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for authentication-related endpoints.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint to register a new user.
     * @param user The user object containing registration details.
     * @return The saved user object.
     */
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    /**
     * Endpoint to simulate a login check.
     * This endpoint is protected and requires valid credentials to access.
     * @return A success message if authenticated.
     */
    @GetMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("Login successful!");
    }
}
