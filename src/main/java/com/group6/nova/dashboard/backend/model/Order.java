package com.group6.nova.dashboard.backend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/// Represents an order entity.
///
/// This entity class maps to the "order" table in the database and contains various fields
/// representing the details of an order, including pricing, VAT, payment status, and order
/// creation time.
///
/// [SuppressWarnings] explanation:
/// - `ClassWithTooManyFields`, `PMD.TooManyFields` - Entity classes may contain multiple fields to
///  represent all the columns in the database.
///
/// @author Martin Kedmenec
/// @see PaymentStatus
/// @see OrderStatus
@Entity
@Table(name = "order")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@SuppressWarnings({"ClassWithTooManyFields", "PMD.TooManyFields"})
public class Order {
  /// `Created` column
  @Column(nullable = false)
  @NonNull
  private ZonedDateTime created;

  /// `Order Number` column
  @Column(nullable = false)
  @NonNull
  private Long orderNumber = 0L;

  /// `OrderVAT Number` column
  @Column(nullable = false)
  @NonNull
  private Integer orderVatNumber;

  /// `Business Date` column
  @Column(nullable = false)
  @NonNull
  private LocalDate businessDate;

  /// `Price` column
  @Column(nullable = false)
  @NonNull
  private BigDecimal price;

  /// `Price excl. VAT` column
  @Column(nullable = false)
  @NonNull
  private BigDecimal priceExclVat;

  /// `VAT` column
  @Column(nullable = false)
  @NonNull
  private BigDecimal vat;

  /// `Tips` column
  @Column(nullable = false)
  @NonNull
  private BigDecimal tips;

  /// `Payment Status` column
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  @NonNull
  private PaymentStatus paymentStatus;

  /// `Order Status` column
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  @NonNull
  private OrderStatus orderStatus;

  /// `Is revenue` column
  @Column(nullable = false)
  @NonNull
  private Boolean isRevenue;

  /// `Order ID` column
  @Id
  @Column(nullable = false)
  @NonNull
  private UUID orderId;

  /// `Order Reference` column
  @Column(nullable = false)
  @NonNull
  private Integer orderReference;

  /// `isDemo` column
  @Column(nullable = false)
  @NonNull
  private Boolean isDemo;

  /// Entity creation timestamp
  @CreationTimestamp
  @Column(nullable = false)
  private ZonedDateTime createdAt;

  /// Entity update timestamp
  @UpdateTimestamp
  @Column(nullable = false)
  private ZonedDateTime updatedAt;

  /// OrderLines reference
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderLine> orderLines;
}
