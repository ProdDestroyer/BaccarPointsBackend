
package com.baccarpoints1.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "points_history")
public class PointsHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "facility_id", nullable = false)
    private Long facilityId;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "client_id", nullable = false, length = 20)
    private String clientId;

    @Column(name = "added_points", nullable = false)
    private int addedPoints;

    public PointsHistory() {}

    public Long getId() {
        return id;
    }

    public Long getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Long facilityId) {
        this.facilityId = facilityId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getAddedPoints() {
        return addedPoints;
    }

    public void setAddedPoints(int addedPoints) {
        this.addedPoints = addedPoints;
    }
}
