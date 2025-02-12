package org.beerbower.vanlife.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.beerbower.vanlife.entities.User;
import org.beerbower.vanlife.repositories.UserRepository;
import org.beerbower.vanlife.security.JwtUtils;

@RestController
@PreAuthorize("permitAll()")
@CrossOrigin
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration ;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Find user by email
        User user = userRepository.findByEmail(request.email()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        // Validate password
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        // Generate JWT
        String jwt = JwtUtils.createJwt(user, jwtSecret, jwtExpiration);

        return ResponseEntity.ok(new LoginResponse(jwt, user));
    }

    public record LoginRequest(String email, String password) {}

    public record LoginResponse(String jwt, User user) {}
}
