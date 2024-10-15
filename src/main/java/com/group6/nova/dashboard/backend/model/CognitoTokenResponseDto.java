package com.group6.nova.dashboard.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/// Represents the response object of the AWS Cognito token endpoint.
///
/// @author Martin Kedmenec
/// @param accessToken an access token
/// @param idToken an ID token
/// @param refreshToken a refresh token
/// @param tokenType the token type
/// @param expiresIn the expiration time
@JsonIgnoreProperties(ignoreUnknown = true)
public record CognitoTokenResponseDto(
    String accessToken, String idToken, String refreshToken, String tokenType, int expiresIn) {}
