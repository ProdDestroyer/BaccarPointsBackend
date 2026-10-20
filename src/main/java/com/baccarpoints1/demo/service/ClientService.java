package com.baccarpoints1.demo.service;

import com.baccarpoints1.demo.repository.PointsHistoryRepository;

import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import com.baccarpoints1.demo.entity.Client;
import com.baccarpoints1.demo.exceptions.ClientAlreadyExistsException;
import com.baccarpoints1.demo.exceptions.ClientNotFoundException;
import com.baccarpoints1.demo.repository.ClientRepository;

import jakarta.transaction.Transactional;

@Service
public class ClientService {

    private final PointsHistoryRepository pointsHistoryRepository;
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository, PointsHistoryRepository pointsHistoryRepository) {
        this.clientRepository = clientRepository;
        this.pointsHistoryRepository = pointsHistoryRepository;
    }

    public Client getClient(String id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(
                        "Client with ID " + id + " not found."));
    }

    @Transactional
    public boolean addPoints(String id, int points, Long facilityId) {
        if (points <= 0) {
            throw new IllegalArgumentException("Points must be greater than zero.");
        }

        int updated = clientRepository.addPoints(id, points);

        if (updated == 0) {
            return false;
        }

        pointsHistoryRepository.insertHistory(facilityId, id, points);

        return true;
    }

    @Transactional
    public Client createClient(
            String id,
            String firstName,
            String lastName,
            String whatsappNumber,
            int initialPoints,
            Long facilityId) {

        if (clientRepository.existsById(id)) {
            throw new ClientAlreadyExistsException(
                    "A client with ID " + id + " already exists.");
        }

        if (initialPoints < 0) {
            throw new IllegalArgumentException(
                    "Initial points cannot be negative.");
        }

        Client client = new Client();
        client.setId(id);
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setWhatsappNumber(whatsappNumber);
        client.setPoints(initialPoints);

        Client savedClient = clientRepository.saveAndFlush(client);

        if (initialPoints > 0) {
            pointsHistoryRepository.insertHistory(
                    facilityId, id, initialPoints);
        }

        return savedClient;
    }
}
