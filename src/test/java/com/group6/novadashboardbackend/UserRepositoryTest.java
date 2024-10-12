package com.group6.novadashboardbackend;

import static org.assertj.core.api.Assertions.assertThat;

import com.group6.novadashboardbackend.model.User;
import com.group6.novadashboardbackend.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

/// Tests for the [UserRepository] class.
@ToString
@SpringBootTest
@Import(TestcontainersConfiguration.class)
@Transactional
class UserRepositoryTest {
  /// [EntityManager] instance
  @PersistenceContext private EntityManager entityManager;

  /// [UserRepository] instance
  @Autowired private UserRepository userRepository;

  /// Constructor.
  /* default */ UserRepositoryTest() {
    //
  }

  @Test
  final void testSaveAndFind() {
    final User user = new User("John", "Doe", "john.doe@example.com", "Password987");
    final User savedUser = userRepository.save(user);

    final Long savedUserId = savedUser.getId();
    final User foundUser = entityManager.find(User.class, savedUserId);

    assertThat(user).isEqualTo(foundUser);
  }

  @Test
  final void testUpdate() {
    final User user =
        new User("Someone", "Interesting", "someone.interesting@example.com", "Password123");
    entityManager.persist(user);

    final String newEmail = "someone@example.com";
    user.setEmail(newEmail);

    userRepository.save(user);

    final Long userId = user.getId();
    final User foundUser = entityManager.find(User.class, userId);
    final String foundUserEmail = foundUser.getEmail();

    assertThat(newEmail).isEqualTo(foundUserEmail);
  }
}
