package com.baccarpoints1.demo.repository;

import com.baccarpoints1.demo.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientRepository extends JpaRepository<Client, String> {

    @Modifying
    @Query("UPDATE Client c SET c.points = c.points + :points WHERE c.id = :id")
    int addPoints(@Param("id") String id, @Param("points") int points);
}