package com.group6.novadashboardbackend.configuration;

import static com.group6.novadashboardbackend.configuration.AllowedHeader.AUTHORIZATION;

import com.group6.novadashboardbackend.service.JwtServiceInterface;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

/// JwtAuthenticationFilter is responsible for filtering incoming HTTP requests to check for a JWT
/// token.
/// It extends [OncePerRequestFilter] to ensure the filtering logic is executed once per request.
/// It uses the following services:
/// - [JwtServiceInterface] to handle JWT operations such as extracting username and validating
/// tokens.
/// - [UserDetailsService] to retrieve user details for authentication.
/// - [HandlerExceptionResolver] to handle any exceptions that may arise during the filtering
/// process.
/// The filter checks for the presence of an Authorization header that starts with "Bearer ".
/// If such a header is found, it extracts the JWT token and the username, retrieves the user
/// details,
/// and validates the token. If the token is valid, it sets the authentication in the
/// SecurityContext.
/// If an exception occurs during this process, it is handled by the [HandlerExceptionResolver].
@ToString
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  /// [JwtServiceInterface] instance
  private final JwtServiceInterface jwtServiceInterface;

  /// [UserDetailsService] instance
  private final UserDetailsService userDetailsService;

  /// [HandlerExceptionResolver] instance
  private final HandlerExceptionResolver handlerExceptionResolver;

  /// Constructor.
  /// @param jwtServiceInterfaceParameter JwtServiceInterface instance
  /// @param userDetailsServiceParameter UserDetailsService instance
  /// @param handlerExceptionResolverParameter HandlerExceptionResolver instance
  public JwtAuthenticationFilter(
      final JwtServiceInterface jwtServiceInterfaceParameter,
      final UserDetailsService userDetailsServiceParameter,
      @Qualifier("handlerExceptionResolver")
          final HandlerExceptionResolver handlerExceptionResolverParameter) {
    jwtServiceInterface = jwtServiceInterfaceParameter;
    userDetailsService = userDetailsServiceParameter;
    handlerExceptionResolver = handlerExceptionResolverParameter;
  }

  @Override
  protected final void doFilterInternal(
      @NonNull final HttpServletRequest request,
      @NonNull final HttpServletResponse response,
      @NonNull final FilterChain filterChain)
      throws ServletException, IOException {
    final String authHeader = request.getHeader(AUTHORIZATION);

    if (null == authHeader || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);

      return;
    }

    final String token = authHeader.substring(7);
    final String userEmail = jwtServiceInterface.extractUsername(token);
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (null == userEmail || null != authentication) {
      return;
    }

    final UserDetails userDetails;

    try {
      userDetails = userDetailsService.loadUserByUsername(userEmail);
    } catch (final UsernameNotFoundException exception) {
      handlerExceptionResolver.resolveException(request, response, null, exception);

      return;
    }

    if (!jwtServiceInterface.isTokenValid(token, userDetails)) {
      return;
    }

    final Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
    final UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    final WebAuthenticationDetails details =
        new WebAuthenticationDetailsSource().buildDetails(request);

    authToken.setDetails(details);

    SecurityContextHolder.getContext().setAuthentication(authToken);

    filterChain.doFilter(request, response);
  }
}
