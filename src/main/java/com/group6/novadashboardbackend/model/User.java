package com.group6.novadashboardbackend.model;

import static com.group6.novadashboardbackend.WarningValue.DESIGN_FOR_EXTENSION;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/// Represents a user in the system.
/// This entity is used to store user details and for authentication purposes.
/// It implements UserDetails for integration with Spring Security.
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "user")
@SuppressWarnings("PMD.ShortClassName") // User is a short but correct class name
public class User implements UserDetails {
  @Serial private static final long serialVersionUID = 8005846822059778972L;

  /// User ID
  @Id
  @GeneratedValue
  @SuppressWarnings("PMD.ShortVariable")
  private Long id;

  /// First name
  @Column(nullable = false)
  @NonNull
  private String firstName;

  /// Last name
  @Column(nullable = false)
  @NonNull
  private String lastName;

  /// Email
  @Column(unique = true, length = 50, nullable = false)
  @NonNull
  private String email;

  /// Password
  @Column(nullable = false)
  @NonNull
  private String password;

  /// Created at
  @CreationTimestamp
  @Column(updatable = false, nullable = false)
  private LocalDateTime createdAt;

  /// Updated at
  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @Override
  public final Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  @Override
  @NonNull
  @SuppressWarnings(DESIGN_FOR_EXTENSION) // Getter methods of lazy classes cannot be final.
  public String getPassword() {
    return password;
  }

  @Override
  @SuppressWarnings("SuspiciousGetterSetter") // Username getter returns email.
  public final String getUsername() {
    return email;
  }

  @Override
  public final boolean isAccountNonExpired() {
    return UserDetails.super.isAccountNonExpired();
  }

  @Override
  public final boolean isAccountNonLocked() {
    return UserDetails.super.isAccountNonLocked();
  }

  @Override
  public final boolean isCredentialsNonExpired() {
    return UserDetails.super.isCredentialsNonExpired();
  }

  @Override
  public final boolean isEnabled() {
    return UserDetails.super.isEnabled();
  }
}
