package com.group6.novadashboardbackend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;
import javax.crypto.SecretKey;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/// A service class for managing JSON Web Tokens (JWT).
/// This class provides methods to generate, validate, and extract information from JWTs.
@ToString
@Service
public class JwtService implements JwtServiceInterface {
  /// The value of the secret key as configured in application.yml.
  @Value("${security.jwt.secret-key}")
  private String secretKey;

  /// The value of the expiration time as configured in application.yml.
  @Getter
  @Value("${security.jwt.expiration-time}")
  private long expirationTime;

  /// Constructor.
  public JwtService() {
    //
  }

  private SecretKey getSignInKey() {
    final byte[] keyBytes = Decoders.BASE64.decode(secretKey);

    return Keys.hmacShaKeyFor(keyBytes);
  }

  private <T> T extractClaim(
      final CharSequence token, final Function<? super Claims, T> claimsResolver) {
    final SecretKey signInKey = getSignInKey();
    final Jws<Claims> claims = Jwts.parser().verifyWith(signInKey).build().parseSignedClaims(token);
    final Claims payload = claims.getPayload();

    return claimsResolver.apply(payload);
  }

  /// {@inheritDoc}
  /// This is the default method implementation.
  @Override
  public final String generateToken(final UserDetails user) {
    final String username = user.getUsername();
    final SecretKey signInKey = getSignInKey();

    return Jwts.builder()
        .claims(new HashMap<>())
        .subject(username)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + expirationTime))
        .signWith(signInKey)
        .compact();
  }

  /// {@inheritDoc}
  /// This is the default method implementation.
  @Override
  public final boolean isTokenValid(final CharSequence token, final UserDetails userDetails) {
    final String tokenUsername = extractClaim(token, Claims::getSubject);
    final String databaseUsername = userDetails.getUsername();

    return tokenUsername.equals(databaseUsername)
        && !extractClaim(token, Claims::getExpiration).before(new Date());
  }

  /// {@inheritDoc}
  /// This is the default method implementation.
  @Override
  public final String extractUsername(final CharSequence token) {
    return extractClaim(token, Claims::getSubject);
  }
}
