package com.group6.novadashboardbackend;

import com.group6.novadashboardbackend.model.User;
import com.group6.novadashboardbackend.repository.UserRepository;
import java.util.Collection;
import java.util.HashSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

///
@SpringBootTest
final class UserTest {
  ///
  private final UserRepository userRepository;

  @Autowired
  private UserTest(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Test
  void userSetTest() {
    final User user = new User("John", "Doe", "john.doe@example.com", "Password123!");
    final Collection<User> set = new HashSet<>(1);

    set.add(user);
    userRepository.save(user);

    final boolean isUserInSet = set.contains(user);

    Assertions.assertTrue(isUserInSet, "User not in set");
  }
}
