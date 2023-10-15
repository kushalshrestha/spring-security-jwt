package com.kushal.springsecurity1.auth;


import com.kushal.springsecurity1.config.JwtService;
import com.kushal.springsecurity1.model.User;
import com.kushal.springsecurity1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        try {
            if (repository.findByEmail(request.getEmail())
                          .isPresent()) {
                throw new DuplicateKeyException("User already exists.");
            }


            var user = User.builder()
                           .email(request.getEmail())
                           .password(passwordEncoder.encode(request.getPassword()))
                           .role(request.getRole())
                           .build();
            repository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                                         .response(jwtToken)
                                         .build();
        } catch (DuplicateKeyException ex) {
            System.out.println("DuplicateKeyException: " + ex.getMessage());

            // Return a custom response to the client
            return AuthenticationResponse.builder().response("Email already exists").build();
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = repository.findByEmail(request.getEmail())
                             .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                                     .response(jwtToken)
                                     .build();
    }
}

