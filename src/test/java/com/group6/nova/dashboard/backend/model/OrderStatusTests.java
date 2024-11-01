package com.group6.nova.dashboard.backend.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/// Tests of the [OrderStatus] enum.
///
/// @author Martin Kedmenec
/// @see OrderStatus
@SpringBootTest
@NoArgsConstructor
class OrderStatusTests {
  @Test
  final void testOrderStatusArchivedFromString() {
    final OrderStatus orderStatus = OrderStatus.fromString("ARCHIVED");

    assertThat(orderStatus).isEqualTo(OrderStatus.ARCHIVED);
  }

  @Test
  final void testOrderStatusDeletedFromString() {
    final OrderStatus orderStatus = OrderStatus.fromString("DELETED");

    assertThat(orderStatus).isEqualTo(OrderStatus.DELETED);
  }

  @Test
  final void testInvalidOrderStatus() {
    final String invalidOrderStatus = "CREATED";

    assertThatThrownBy(() -> OrderStatus.fromString(invalidOrderStatus))
        .isInstanceOf(UnknownStatusException.class)
        .hasMessage("Unknown order status: %s", invalidOrderStatus);
  }
}
