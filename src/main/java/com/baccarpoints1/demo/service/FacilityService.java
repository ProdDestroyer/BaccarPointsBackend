package com.baccarpoints1.demo.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baccarpoints1.demo.entity.Facility;
import com.baccarpoints1.demo.repository.FacilityRepository;

import jakarta.transaction.Transactional;

@Service
public class FacilityService {

    private final FacilityRepository facilityRepository;
    private final PasswordEncoder passwordEncoder;

    public FacilityService(
            FacilityRepository facilityRepository,
            PasswordEncoder passwordEncoder) {

        this.facilityRepository = facilityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Facility createFacility(String username, String password) {
        Facility facility = new Facility();
        facility.setUsername(username);
        facility.setPassword(passwordEncoder.encode(password));

        return facilityRepository.save(facility);
    }

    @Transactional
    public boolean updatePassword(
            Long facilityId,
            String currentPassword,
            String newPassword) {

        Facility facility = facilityRepository.findById(facilityId)
                .orElse(null);

        if (facility == null
                || !passwordEncoder.matches(
                        currentPassword, facility.getPassword())) {
            return false;
        }

        facility.setPassword(passwordEncoder.encode(newPassword));
        facilityRepository.save(facility);

        return true;
    }

    @Transactional
    public boolean updateUserName(
            Long facilityId,
            String newUsername,
            String currentPassword) {

        Facility facility = facilityRepository.findById(facilityId)
                .orElse(null);

        if (facility == null
                || !passwordEncoder.matches(
                        currentPassword, facility.getPassword())) {
            return false;
        }

        if (facilityRepository.existsByUsername(newUsername)) {
            return false;
        }

        facility.setUsername(newUsername);
        facilityRepository.save(facility);

        return true;
    }

    public List<Facility> getFacilities() {
        return facilityRepository.findAll();
    }

    public Facility getFacility(Long id) {
        return facilityRepository.findById(id).orElse(null);
    }

    public Facility getFacilityByUsername(String username) {
        return facilityRepository.findByUsername(username);
    }
}