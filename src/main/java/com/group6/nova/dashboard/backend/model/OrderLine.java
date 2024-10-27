package com.group6.nova.dashboard.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/// Represents the order line entity.
///
/// @author Martin Kedmenec
@Entity
@Table(name = "order_line")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@SuppressWarnings({"ClassWithTooManyFields", "PMD.TooManyFields"})
public class OrderLine {
  /// `Orderline ID` column
  @Id
  @Column(nullable = false)
  @NonNull
  private UUID orderLineId;

  /// `Product ID` column
  @Column(nullable = false)
  @NonNull
  private UUID productId;

  /// `Title` column
  @Column(nullable = false)
  @NonNull
  private String title;

  /// `Quantity` column
  @Column(nullable = false)
  @NonNull
  private Integer quantity;

  /// `Price` column
  @NonNull private BigDecimal price;

  /// `Unit price` column
  @NonNull private BigDecimal unitPrice;

  /// `Unit price discounted` column
  @NonNull private BigDecimal unitPriceDiscounted;

  /// `System product` column
  @Column(nullable = false)
  @NonNull
  private Boolean systemProduct;

  /// `Quantity Unit` column
  private String quantityUnit;

  /// `Cancelled` column
  @Column(nullable = false)
  @NonNull
  private Boolean cancelled;

  /// Entity creation timestamp
  @CreationTimestamp
  @Column(nullable = false)
  private ZonedDateTime createdAt;

  /// Entity update timestamp
  @UpdateTimestamp
  @Column(nullable = false)
  private ZonedDateTime updatedAt;

  /// Order reference
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;
}
