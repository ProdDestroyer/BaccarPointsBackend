
package com.baccarpoints1.demo.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final FacilityService facilityService;

    public AuthService(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            FacilityService facilityService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.facilityService = facilityService;
    }

    public String login(String username, String password) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        var facility = facilityService.getFacilityByUsername(
                authentication.getName());

        return jwtService.generateToken(
                facility.getUsername(),
                facility.getId());
    }
}
