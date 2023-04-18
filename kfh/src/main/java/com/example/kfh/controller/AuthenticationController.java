package com.example.kfh.controller;

import com.example.kfh.dto.AuthenticationRequest;
import com.example.kfh.dto.AuthenticationResponse;
import com.example.kfh.service.AuthenticationLoggerService;
import com.example.kfh.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthenticationLoggerService authenticationLoggerService;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest authenticationRequest,
            HttpServletRequest servletRequest
    ) {
        authenticationLoggerService.log(authenticationRequest, servletRequest);

        logger.info("Logging attempt");

        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }
}
