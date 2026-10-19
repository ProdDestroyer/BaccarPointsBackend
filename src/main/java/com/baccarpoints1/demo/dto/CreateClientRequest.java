package com.baccarpoints1.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateClientRequest {

    @NotBlank(message = "Client ID is required.")
    @Size(max = 20, message = "Client ID must not exceed 20 characters.")
    private String id;

    @NotBlank(message = "First name is required.")
    @Size(max = 25, message = "First name must not exceed 25 characters.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(max = 20, message = "Last name must not exceed 20 characters.")
    private String lastName;

    @Min(value = 0, message = "Initial points cannot be negative.")
    private int points;

    public CreateClientRequest() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
