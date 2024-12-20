package com.group6.nova.dashboard.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
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

/// Represents the order line entity.
///
/// @author Martin Kedmenec
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@SuppressWarnings({"ClassWithTooManyFields", "PMD.TooManyFields"})
public class OrderLine {
  /// `Orderline ID` column
  @Id
  @NotNull
  @SuppressWarnings("PMD.ShortVariable")
  private UUID id;

  /// `Quantity` column
  @NotNull private Integer quantity;

  /// `Price` column
  @NotNull private BigDecimal price;

  /// `Unit price` column
  @NotNull private BigDecimal unitPrice;

  /// `Unit price discounted` column
  @NotNull private BigDecimal unitPriceDiscounted;

  /// `System product` column
  @NotNull private Boolean systemProduct;

  /// `Quantity Unit` column
  private String quantityUnit;

  /// `Cancelled` column
  @NotNull private Boolean cancelled;

  /// Entity creation timestamp
  @CreationTimestamp
  @Column(updatable = false)
  @ColumnDefault("now()")
  private ZonedDateTime createdAt;

  /// Entity update timestamp
  @UpdateTimestamp
  @ColumnDefault("now()")
  private ZonedDateTime updatedAt;

  /// Order reference
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
  @Exclude
  private Order order;

  /// Product reference
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
  @Exclude
  private Product product;
}
