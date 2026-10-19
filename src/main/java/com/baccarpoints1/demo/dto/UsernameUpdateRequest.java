package com.baccarpoints1.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsernameUpdateRequest {

    @NotBlank
    @Size(max = 50)
    private String newUsername;

    @NotBlank
    @Size(max = 72)
    private String password;

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
