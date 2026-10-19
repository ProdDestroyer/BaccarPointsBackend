
package com.baccarpoints1.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.baccarpoints1.demo.entity.PointsHistory;

public interface PointsHistoryRepository
        extends JpaRepository<PointsHistory, Long> {

    @Modifying
    @Query(value = """
        INSERT INTO points_history (facility_id, client_id, added_points)
        VALUES (:facilityId, :clientId, :points)
        """, nativeQuery = true)
    int insertHistory(
            @Param("facilityId") Long facilityId,
            @Param("clientId") String clientId,
            @Param("points") int points);
}