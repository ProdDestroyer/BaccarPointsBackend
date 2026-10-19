package com.baccarpoints1.demo.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baccarpoints1.demo.dto.AddPointsRequest;
import com.baccarpoints1.demo.dto.CreateClientRequest;
import com.baccarpoints1.demo.entity.Client;
import com.baccarpoints1.demo.exceptions.ClientNotFoundException;
import com.baccarpoints1.demo.service.ClientService;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<?> createClient(
            @RequestBody CreateClientRequest request,
            Authentication authentication) {

        Long facilityId = (Long) authentication.getPrincipal();

        try {
            Client client = clientService.createClient(
                    request.getId(),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getPoints(),
                    facilityId);

            return ResponseEntity.status(HttpStatus.CREATED).body(client);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(java.util.Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClient(@PathVariable String id) {
        return ResponseEntity.ok(clientService.getClient(id));
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleClientNotFound(
            ClientNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", ex.getMessage()));
    }

    @PostMapping("/addpoints")
    public ResponseEntity<?> addPoints(
            @RequestBody AddPointsRequest request,
            Authentication authentication) {

        Long facilityId = (Long) authentication.getPrincipal();

        try {
            boolean success = clientService.addPoints(
                    request.getClientId(), request.getPoints(), facilityId);

            if (!success) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(
                    java.util.Map.of("message", "Points added successfully."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(java.util.Map.of("message", e.getMessage()));
        }
    }
}