package com.baccarpoints1.demo.dto;

public class FacilityResponse {

    private final Long id;
    private final String username;

    public FacilityResponse(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}