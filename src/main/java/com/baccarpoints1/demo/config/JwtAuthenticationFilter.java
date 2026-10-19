package com.baccarpoints1.demo.config;

import java.io.IOException;
import java.util.List;

import com.baccarpoints1.demo.repository.FacilityRepository;
import com.baccarpoints1.demo.service.JwtService;
import io.jsonwebtoken.JwtException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final FacilityRepository facilityRepository;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            FacilityRepository facilityRepository) {
        this.jwtService = jwtService;
        this.facilityRepository = facilityRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        try {
            Long facilityId = jwtService.extractFacilityId(token);
            int tokenVersion = jwtService.extractTokenVersion(token);

            var facility = facilityRepository.findById(facilityId).orElse(null);

            if (facility == null || facility.getTokenVersion() != tokenVersion) {
                SecurityContextHolder.clearContext();
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Invalid or revoked token");
                return;
            }

            var authentication = new UsernamePasswordAuthenticationToken(
                    facilityId,
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_FACILITY")));

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

        } catch (JwtException | IllegalArgumentException e) {
            SecurityContextHolder.clearContext();
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Invalid or expired token");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
