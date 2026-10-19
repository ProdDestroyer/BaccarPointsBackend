package com.baccarpoints1.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baccarpoints1.demo.entity.Facility;

public interface FacilityRepository extends JpaRepository<Facility, Long> {

    Facility findByUsername(String username);

    boolean existsByUsername(String username);
}