package com.baccarpoints1.demo.controller;

import java.util.Map;

import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baccarpoints1.demo.dto.CreateFacilityRequest;
import com.baccarpoints1.demo.dto.FacilityResponse;
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
    public ResponseEntity<?> createFacility(
            @Valid @RequestBody CreateFacilityRequest request) {

        try {
            Facility created = facilityService.createFacility(
                    request.getUsername(),
                    request.getPassword());

            FacilityResponse response = new FacilityResponse(
                    created.getId(),
                    created.getUsername());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Username already exists."));
        }
    }

    @PutMapping("/passwordUpdate")
    public ResponseEntity<?> updatePassword(
            @Valid @RequestBody PasswordUpdateRequest request,
            Authentication authentication) {

        Long facilityId = (Long) authentication.getPrincipal();

        boolean updated = facilityService.updatePassword(
                facilityId,
                request.getPassword(),
                request.getNewPassword());

        if (!updated) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message",
                            "Password update failed. Check your current password."));
        }

        return ResponseEntity.ok(
                Map.of("message", "Password updated successfully."));
    }

    @PutMapping("/usernameUpdate")
    public ResponseEntity<?> updateUsername(
            @Valid @RequestBody UsernameUpdateRequest request,
            Authentication authentication) {

        Long facilityId = (Long) authentication.getPrincipal();

        boolean updated = facilityService.updateUserName(
                facilityId,
                request.getNewUsername(),
                request.getPassword());

        if (!updated) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message",
                            "Username update failed. Check your password or username."));
        }

        return ResponseEntity.ok(
                Map.of("message", "Username updated successfully."));
    }
}
