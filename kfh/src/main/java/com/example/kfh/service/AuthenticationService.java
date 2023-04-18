package com.example.kfh.service;

import com.example.demo.config.JwtService;
import com.example.demo.config.SecurityConfiguration;
import com.example.demo.dto.AuthenticationRequest;
import com.example.demo.dto.AuthenticationResponse;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        String message = null;
        try {
            message = "Login was successful";
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
        } catch (AuthenticationException exception) {
            try {
                User user = repository.findByUsername(authenticationRequest.getUsername()).orElseThrow();
                int failedAttemptCount = user.getFailedAttempts();
                if (user.getFailedAttempts() > SecurityConfiguration.MAX_FAILED_ATTEMPTS) {
                    user.setFailedAttempts(failedAttemptCount);
                    user.setActive(false);
                } else {
                    user.setFailedAttempts(failedAttemptCount + 1);
                }
                repository.save(user);
            } catch (NoSuchElementException e) {
                logger.warn("tried to load user that doesn't exist");
            }
            message = "Login or password as incorrect";

            return AuthenticationResponse.builder().error(true).message(message).build();
        }

        User user = repository.findByUsername(authenticationRequest.getUsername()).orElseThrow();

        String jwtToken = jwtService.generateToken(user);

        if (!user.isActive) {
            jwtToken = null;
            message = "Account is deactivated please contact administrator";
        } else {
            user.setFailedAttempts(0);
            repository.save(user);
        }

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .message(message)
                .error(jwtToken == null)
                .role(jwtToken == null ? Role.UNKNOWN : user.getRole())
                .build();
    }
}
