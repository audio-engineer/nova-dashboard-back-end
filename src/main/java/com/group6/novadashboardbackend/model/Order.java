package com.group6.novadashboardbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/// Represents the Order entity.
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "order")
@SuppressWarnings("ClassWithTooManyFields")
public class Order {
  /// Order ID
  @Id private Long orderNumber;

  /// Order VAT number
  private Integer orderVatNumber;

  /// Price
  private BigDecimal price;

  /// Price excl. VAT
  private BigDecimal priceExclVat;

  /// VAT
  private BigDecimal vat;

  /// Tips
  private BigDecimal tips;

  /// Payment status
  private PaymentStatus paymentStatus;

  /// Order status
  private OrderStatus orderStatus;

  /// Is revenue
  private Boolean isRevenue;

  /// Order ID
  private UUID orderId;

  /// Order reference
  private Integer orderReference;

  /// Order created at.
  private LocalDateTime createdAt;

  /// Is demo
  private Boolean isDemo;
}
