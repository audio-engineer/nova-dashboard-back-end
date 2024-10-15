package com.group6.nova.dashboard.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group6.nova.dashboard.backend.model.CognitoTokenResponseDto;
import com.group6.nova.dashboard.backend.model.TokenDto;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

/// Controller that handles AWS Cognito authentication operations.
///
/// Provides endpoints for:
/// - Generating the AWS Cognito sign-in page URL.
/// - Exchanging an authorization code for an access token via the `/callback` endpoint.
///
/// Annotations:
/// - [ToString] - Generates a string representation of the object for debugging.
/// - [RestController] - Marks the class as a REST controller.
/// - [RequestMapping] - Maps the root path for this controller to `/authentication`.
///
/// @author Martin Kedmenec
/// @see ResponseEntity
/// @see URI
@ToString
@RestController
@RequestMapping("authentication")
public class AuthenticationController {
  /// The callback path of the front end client
  private static final String CALLBACK_PATH = "/api/cognito/response";

  /// [ObjectMapper] instance
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  /// AWS Cognito client ID
  @Value("${spring.security.oauth2.resourceserver.jwt.client-id}")
  private String clientId;

  /// AWS Cognito client secret
  @Value("${spring.security.oauth2.resourceserver.jwt.client-secret}")
  private String clientSecret;

  /// AWS Cognito URL
  @Value("${spring.security.oauth2.resourceserver.jwt.cognito-domain}")
  private String cognitoDomain;

  /// Allowed origin
  @Value("${security.cors.allowed-origins}")
  private String allowedOrigins;

  /// Constructor.
  public AuthenticationController() {
    //
  }

  /// Generates the AWS Cognito hosted UI sign-in URL.
  ///
  /// @return the AWS Cognito hosted sign-in page UI URL
  @GetMapping("/cognito-sign-in")
  public final ResponseEntity<URI> getCognitoSignInPageUrl() {
    final URI url =
        UriComponentsBuilder.fromUriString(cognitoDomain)
            .path("/login")
            .queryParam("client_id", "{clientId}")
            .queryParam("response_type", "{responseType}")
            .queryParam("scope", "{scope}")
            .queryParam("redirect_uri", "{redirectUri}")
            .buildAndExpand(clientId, "code", "email+openid+phone", allowedOrigins + CALLBACK_PATH)
            .toUri();

    return ResponseEntity.ok(url);
  }

  /// Exchanges an AWS authorization code for an access token.
  ///
  /// [SuppressWarnings] explanation:
  /// - `LocalCanBeFinal` - A try-with-resources statement cannot declare final variables.
  ///
  /// @param code an AWS authorization code
  /// @return an object containing the token
  @GetMapping("/callback")
  public final ResponseEntity<TokenDto> callback(@RequestParam("code") final String code) {
    final URI url =
        UriComponentsBuilder.fromUriString(cognitoDomain).path("/oauth2/token").build().toUri();

    final String authenticationInfo = clientId + ":" + clientSecret;
    final byte[] bytes = authenticationInfo.getBytes(StandardCharsets.UTF_8);
    final String basicAuthenticationInfo = Base64.getEncoder().encodeToString(bytes);

    final BodyPublisher bodyPublisher =
        BodyPublishers.ofString(
            "grant_type=authorization_code&"
                + "client_id="
                + clientId
                + "&code="
                + code
                + "&redirect_uri="
                + allowedOrigins
                + CALLBACK_PATH);

    final HttpRequest request;

    request =
        HttpRequest.newBuilder(url)
            .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
            .header(HttpHeaders.AUTHORIZATION, "Basic " + basicAuthenticationInfo)
            .POST(bodyPublisher)
            .build();

    final HttpResponse<String> response;

    try (@SuppressWarnings("LocalCanBeFinal")
        HttpClient client = HttpClient.newHttpClient()) {
      try {
        final BodyHandler<String> responseBodyHandler = BodyHandlers.ofString();

        response = client.send(request, responseBodyHandler);
      } catch (final IOException | InterruptedException e) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to request Cognito", e);
      }
    }

    if (HttpStatus.OK.value() != response.statusCode()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Authentication failed");
    }

    final CognitoTokenResponseDto token;
    final String body = response.body();

    try {
      token = OBJECT_MAPPER.readValue(body, CognitoTokenResponseDto.class);
    } catch (final JsonProcessingException e) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Unable to decode Cognito response", e);
    }

    final String token1 = token.idToken();
    return ResponseEntity.ok(new TokenDto(token1));
  }
}
