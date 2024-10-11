package com.group6.novadashboardbackend.repository;

import com.group6.novadashboardbackend.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

/// User repository interface.
public interface UserRepository extends JpaRepository<User, Long> {
  /// Find a user by their email address.
  /// @param email the user's email address
  /// @return User object, if found
  Optional<UserDetails> findByEmail(String email);
}
