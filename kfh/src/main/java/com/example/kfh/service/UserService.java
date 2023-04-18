package com.example.kfh.service;

import com.example.demo.model.User;
import com.example.demo.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    UserRepository userRepository;

    @Override
    public User getUserByName(String name) {
        Optional<User> user = userRepository.findByUsername(name);
        if (user.isEmpty()) {
            throw new NoSuchElementException("User doesn't exist");
        }

        return user.get();
    }

    @Override
    public List<User> getList() {
        return this.userRepository.findAll();
    }
}
