package com.example.kfh.service;


import com.example.kfh.dto.AuthenticationRequest;
import com.example.kfh.model.AuthenticationLogger;
import com.example.kfh.model.repository.AuthenticationLoggerRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationLoggerService implements AuthenticationLoggerServiceInterface {
    private static final String[] IP_HEADERS = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    private final AuthenticationLoggerRepository authenticationLoggerRepository;

    @Override
    public boolean log(AuthenticationRequest authenticationRequest, HttpServletRequest httpServletRequest) {
        StringBuilder headers = new StringBuilder();

        for (String ipHeader : IP_HEADERS) {
            String value = httpServletRequest.getHeader(ipHeader);
            if (value != null) {
                headers.append("Header:" + ipHeader + "value:" + value + "  ");
            }
        }

        AuthenticationLogger authenticationLogger = AuthenticationLogger.builder()
                .username(authenticationRequest.getUsername())
                .date(new Date().toString())
                .headers(headers.toString())
                .build();


        authenticationLoggerRepository.save(authenticationLogger);

        return true;
    }
}
