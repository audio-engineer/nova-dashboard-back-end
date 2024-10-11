package com.group6.novadashboardbackend.model;

/// Represents the response received after a successful login.
/// @param token the JWT token issued upon authentication
/// @param expiresIn the expiration time of the JWT token in milliseconds
public record LoginResponse(String token, long expiresIn) {}
