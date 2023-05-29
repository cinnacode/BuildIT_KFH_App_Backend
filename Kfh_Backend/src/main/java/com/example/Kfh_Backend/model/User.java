package com.example.Kfh_Backend.model;

public class User {
    private String email;
    private String role;

    // Constructors
    public User() {
    }

    public User(String email, String role) {
        this.email = email;
        this.role = role;
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
