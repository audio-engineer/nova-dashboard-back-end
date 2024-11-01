package com.group6.nova.dashboard.backend.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/// Tests of the [PaymentStatus] enum.
///
/// @author Martin Kedmenec
/// @see PaymentStatus
@SpringBootTest
@NoArgsConstructor
class PaymentStatusTests {
  @Test
  final void testPaymentStatusPaidFromString() {
    final PaymentStatus paymentStatus = PaymentStatus.fromString("Paid");

    assertThat(paymentStatus).isEqualTo(PaymentStatus.PAID);
  }

  @Test
  final void testPaymentStatusUnpaidFromString() {
    final PaymentStatus paymentStatus = PaymentStatus.fromString("Unpaid");

    assertThat(paymentStatus).isEqualTo(PaymentStatus.UNPAID);
  }

  @Test
  final void testInvalidOrderStatus() {
    final String invalidPaymentStatus = "invalid";

    assertThatThrownBy(() -> PaymentStatus.fromString(invalidPaymentStatus))
        .isInstanceOf(UnknownStatusException.class)
        .hasMessage("Unknown payment status: %s", invalidPaymentStatus);
  }
}
