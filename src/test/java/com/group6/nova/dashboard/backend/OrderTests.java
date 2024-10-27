package com.group6.nova.dashboard.backend;

import static org.assertj.core.api.Assertions.assertThat;

import com.group6.nova.dashboard.backend.model.Order;
import com.group6.nova.dashboard.backend.model.OrderStatus;
import com.group6.nova.dashboard.backend.model.PaymentStatus;
import com.group6.nova.dashboard.backend.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

/// Tests of the [Order] class.
///
/// @author Martin Kedmenec
/// @see Order
@DataJpaTest
@Import(TestcontainersConfiguration.class)
@NoArgsConstructor
@ToString
@Transactional
@SuppressWarnings("NestedMethodCall")
class OrderTests {
  ///
  private static Order order;

  ///
  @PersistenceContext private EntityManager entityManager;

  ///
  @Autowired private OrderRepository orderRepository;

  @BeforeAll
  static void setUp() {
    order = new Order();

    order.setCreated(ZonedDateTime.of(2024, 10, 24, 10, 5, 4, 0, ZoneId.of("UTC+02:00")));
    order.setOrderNumber(2L);
    order.setOrderVatNumber(2);
    order.setBusinessDate(LocalDate.of(2024, 10, 24));
    order.setPrice(BigDecimal.valueOf(0.00));
    order.setPriceExclVat(BigDecimal.valueOf(0.00));
    order.setVat(BigDecimal.valueOf(25.00));
    order.setTips(BigDecimal.valueOf(0.00));
    order.setPaymentStatus(PaymentStatus.PAID);
    order.setOrderStatus(OrderStatus.ARCHIVED);
    order.setIsRevenue(true);
    order.setOrderId(UUID.randomUUID());
    order.setOrderReference(1);
    order.setIsDemo(false);
  }

  @Test
  final void orderSaveTest() {
    final Collection<Order> orders = new HashSet<>(2);

    orders.add(order);

    orderRepository.save(order);

    assertThat(orders).contains(order);
  }

  @Test
  final void orderFindTest() {
    entityManager.persist(order);

    final Order firstFetched = entityManager.find(Order.class, order.getOrderId());
    //    entityManager.detach(firstFetched);

    final Order secondFetched = entityManager.find(Order.class, order.getOrderId());

    assertThat(firstFetched).isEqualTo(secondFetched);
  }
}
