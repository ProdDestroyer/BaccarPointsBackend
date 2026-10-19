package com.baccarpoints1.demo.dto;

public class AddPointsRequest {
    private int points;
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