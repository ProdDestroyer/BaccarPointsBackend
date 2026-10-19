package com.baccarpoints1.demo.dto;

public class UsernameUpdateRequest {

    private String username;
    private String newUsername;
    private String password;

    public UsernameUpdateRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String newUserName) {
        this.username = newUserName;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}