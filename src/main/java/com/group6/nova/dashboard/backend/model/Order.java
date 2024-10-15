package com.group6.nova.dashboard.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/// Represents an order entity.
///
/// This entity class maps to the "order" table in the database and contains various fields
/// representing the details of an order, including pricing, VAT, payment status, and order
/// creation time.
///
/// [SuppressWarnings] explanation:
/// - `ClassWithTooManyFields` - Entity classes may contain multiple fields to represent all the
/// columns in the database.
///
/// Annotations:
/// - [AllArgsConstructor] - Generates a constructor with all fields as parameters.
/// - [ToString] - Generates a string representation of the object for debugging.
/// - [Getter], [Setter] - Generates getter and setter methods for all fields.
/// - [Entity] - Marks the class as a JPA entity.
/// - [Table] - Specifies the table associated with the entity.
///
/// @author Martin Kedmenec
/// @see PaymentStatus
/// @see OrderStatus
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

  /// Constructor.
  public Order() {
    //
  }
}
