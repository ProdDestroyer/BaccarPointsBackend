package com.baccarpoints1.demo.controller;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baccarpoints1.demo.dto.PasswordUpdateRequest;
import com.baccarpoints1.demo.dto.UsernameUpdateRequest;
import com.baccarpoints1.demo.entity.Facility;
import com.baccarpoints1.demo.service.FacilityService;

@RestController
@RequestMapping("/facilities")
public class FacilityController {

    private final FacilityService facilityService;

    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @PostMapping
    public ResponseEntity<?> createFacility(@RequestBody Facility facility) {
        Facility created = facilityService.createFacility(facility);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/passwordUpdate")
    public ResponseEntity<?> updatePassword(
            @RequestBody PasswordUpdateRequest request) {

        boolean updated = facilityService.updatePassword(
                request.getUsername(),
                request.getPassword(),
                request.getNewPassword());

        if (!updated) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Password update failed"));
        }

        return ResponseEntity.ok(
                Map.of("message", "Password updated successfully"));
    }

    @PutMapping("/usernameUpdate")
    public ResponseEntity<?> updateUsername(
            @RequestBody UsernameUpdateRequest request) {

        boolean updated = facilityService.updateUserName(
                request.getUsername(),
                request.getNewUsername(),
                request.getPassword());

        if (!updated) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Username update failed"));
        }

        return ResponseEntity.ok(
                Map.of("message", "Username updated successfully"));
    }
}