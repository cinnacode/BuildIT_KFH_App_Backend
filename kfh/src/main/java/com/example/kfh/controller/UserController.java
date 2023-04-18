package com.example.kfh.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.response.GenericResponse;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("list")
    @ResponseBody
    public ResponseEntity<Object> getList() {
        try {
            List<User> users = userService.getList();
            List<UserDTO> result = new ArrayList<UserDTO>();

            users.forEach(user -> {
                UserDTO userDTO = new UserDTO().convert(user);
                result.add(userDTO);

            });
            return GenericResponse.generateResponse("OK", HttpStatus.OK, result);
        } catch (RuntimeException e) {
            return GenericResponse.generateResponse(GenericResponse.GENERIC_MESSAGE, HttpStatus.MULTI_STATUS, null);
        }
    }

    @GetMapping("getByName")
    @ResponseBody
    public ResponseEntity<Object> getByName(
            @RequestParam(name = "username", required = true)
            String username
    ) {
        try {
            User user = userService.getUserByName(username);
            if (user.getUsername().length() == 0) {
                return GenericResponse.generateResponse("No results", HttpStatus.OK, null);
            }

            UserDTO userDTO = new UserDTO().convert(user);

            return GenericResponse.generateResponse("OK", HttpStatus.OK, userDTO);

        } catch (RuntimeException e) {
            return GenericResponse.generateResponse(GenericResponse.GENERIC_MESSAGE, HttpStatus.MULTI_STATUS, null);
        }
    }
}
