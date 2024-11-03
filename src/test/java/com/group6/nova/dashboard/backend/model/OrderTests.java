package com.group6.nova.dashboard.backend.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.group6.nova.dashboard.backend.TestcontainersConfiguration;
import com.group6.nova.dashboard.backend.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUtil;
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
import org.springframework.test.context.jdbc.Sql;
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
@SuppressWarnings({"NestedMethodCall", "MagicNumber"})
class OrderTests {
  /// [Order] instance
  private static final Order ORDER = new Order();

  ///
  private static final UUID ORDER_ID = UUID.fromString("190d7c9e-c71b-4453-999a-0f8afd96d717");

  /// [PersistenceUtil] instance
  private final PersistenceUtil persistenceUnitUtil = Persistence.getPersistenceUtil();

  /// [EntityManager] instance
  @PersistenceContext private EntityManager entityManager;

  /// [OrderRepository] instance
  @Autowired private OrderRepository orderRepository;

  @BeforeAll
  static void setUp() {
    ORDER.setId(ORDER_ID);
    ORDER.setCreated(ZonedDateTime.of(2024, 10, 24, 10, 5, 4, 0, ZoneId.of("UTC+02:00")));
    ORDER.setOrderNumber(2L);
    ORDER.setOrderVatNumber(2);
    ORDER.setBusinessDate(LocalDate.of(2024, 10, 24));
    ORDER.setPrice(BigDecimal.valueOf(0.00));
    ORDER.setPriceExclVat(BigDecimal.valueOf(0.00));
    ORDER.setVat(BigDecimal.valueOf(25.00));
    ORDER.setTips(BigDecimal.valueOf(0.00));
    ORDER.setPaymentStatus(PaymentStatus.PAID);
    ORDER.setOrderStatus(OrderStatus.ARCHIVED);
    ORDER.setIsRevenue(true);
    ORDER.setOrderReference(1);
    ORDER.setIsDemo(false);
    ORDER.setUpdatedAt(ZonedDateTime.of(2024, 10, 24, 10, 5, 4, 0, ZoneId.of("UTC+02:00")));
  }

  @Test
  final void orderSaveTest() {
    final Collection<Order> orders = new HashSet<>(2);

    orders.add(ORDER);

    orderRepository.save(ORDER);

    assertThat(orders).contains(ORDER);
  }

  @Test
  final void orderFindTest() {
    entityManager.persist(ORDER);

    final Order firstFetched = entityManager.find(Order.class, ORDER.getId());
    //    entityManager.detach(firstFetched);

    final Order secondFetched = entityManager.find(Order.class, ORDER.getId());

    assertThat(firstFetched).isEqualTo(secondFetched);
  }

  @Test
  @Sql(
      scripts = "insert-order-with-order-lines.sql",
      executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(
      scripts = "delete-order-with-order-lines.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  final void lazyAssociationsTest() {
    final Order order = orderRepository.findById(ORDER_ID).orElseThrow();

    assertThat(persistenceUnitUtil.isLoaded(order.getOrderLines())).isFalse();

    //    final String orderString = order.toString();

    //    assertThat(persistenceUnitUtil.isLoaded(order.getOrderLines())).isFalse();
  }

  @Test
  final void equalsAndHashCodeTest() {
    orderRepository.save(ORDER);

    final Order order = orderRepository.findById(ORDER_ID).orElseThrow();
    final Order proxy = orderRepository.getReferenceById(ORDER_ID);

    final Collection<Order> orders = new HashSet<>(3);

    orders.add(order);
    orders.add(proxy);

    assertThat(orders.size()).isEqualTo(1);
  }
}
