package com.baccarpoints1.demo.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baccarpoints1.demo.entity.Facility;
import com.baccarpoints1.demo.repository.FacilityRepository;

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

    public Facility createFacility(Facility facility) {
        facility.setPassword(passwordEncoder.encode(facility.getPassword()));
        return facilityRepository.save(facility);
    }

    public boolean updatePassword(String username, String password, String newPassword) {

        Facility facility = facilityRepository.findByUsername(username);

        if (facility == null) {
            return false;
        }

        if (!passwordEncoder.matches(password, facility.getPassword())) {
            return false;
        }

        facility.setPassword(passwordEncoder.encode(newPassword));
        facilityRepository.save(facility);

        return true;
    }

    public boolean updateUserName(String username, String newUsername, String password) {

        Facility facility = facilityRepository.findByUsername(username);
        
        if (facility == null) {
            return false;
        }

        Facility newUserNameFacility = facilityRepository.findByUsername(newUsername);

        if(newUserNameFacility != null) {
            return false;
        }

        if (!passwordEncoder.matches(password, facility.getPassword())) {
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