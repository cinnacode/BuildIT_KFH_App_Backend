package com.example.kfh.service;


import com.example.demo.dto.AuthenticationRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationLoggerServiceInterface {
    public boolean log(AuthenticationRequest authenticationRequest, HttpServletRequest servletRequest);
}
