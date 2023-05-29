package com.example.Kfh_Backend.service;

import com.example.Kfh_Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void addUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void changeUserRole(long userId, String newRole) {
        Optional<User> optionalUser = userRepository.findById(userId);
        optionalUser.ifPresent(user -> {
            user.setRole(newRole);
            userRepository.save(user);
        });
    }

    public void removeUser(long userId) {
        userRepository.deleteById(userId);
    }
}
