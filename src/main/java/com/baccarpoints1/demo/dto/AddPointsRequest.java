package com.baccarpoints1.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class AddPointsRequest {

    @Positive(message = "Points must be greater than zero.")
    private int points;

    @NotBlank(message = "Client ID is required.")
    @Size(max = 20, message = "Client ID must not exceed 20 characters.")
    private String clientId;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
