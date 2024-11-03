package com.group6.nova.dashboard.backend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.annotations.ColumnDefault;
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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@SuppressWarnings({"ClassWithTooManyFields", "PMD.TooManyFields"})
public class Order {
  /// `Order ID` column
  @Id
  @NotNull
  @SuppressWarnings("PMD.ShortVariable")
  private UUID id;

  /// `Created` column
  @NotNull private ZonedDateTime created;

  /// `Order Number` column
  @NotNull private Long orderNumber;

  /// `OrderVAT Number` column
  @NotNull private Integer orderVatNumber;

  /// `Business Date` column
  @NotNull private LocalDate businessDate;

  /// `Price` column
  @NotNull private BigDecimal price;

  /// `Price excl. VAT` column
  @NotNull private BigDecimal priceExclVat;

  /// `VAT` column
  @NotNull private BigDecimal vat;

  /// `Tips` column
  @NotNull private BigDecimal tips;

  /// `Payment Status` column
  @Enumerated(EnumType.STRING)
  @NotNull
  private PaymentStatus paymentStatus;

  /// `Order Status` column
  @Enumerated(EnumType.STRING)
  @NotNull
  private OrderStatus orderStatus;

  /// `Is revenue` column
  @NotNull private Boolean isRevenue;

  /// `Order Reference` column
  @NotNull private Integer orderReference;

  /// `isDemo` column
  @NotNull private Boolean isDemo;

  /// Entity creation timestamp
  @CreationTimestamp
  @Column(insertable = false, updatable = false)
  @ColumnDefault("now()")
  private ZonedDateTime createdAt;

  /// Entity update timestamp
  @UpdateTimestamp
  @Column(insertable = false, updatable = false)
  @ColumnDefault("now()")
  private ZonedDateTime updatedAt;

  /// OrderLines reference
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  @Exclude
  private List<OrderLine> orderLines;
}
