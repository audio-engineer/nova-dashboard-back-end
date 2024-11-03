package com.group6.nova.dashboard.backend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/// Represents the product entity.
///
/// @author Martin Kedmenec
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Product {
  /// Product ID
  @Id
  @NotNull
  @NonNull
  @SuppressWarnings("PMD.ShortVariable")
  private UUID id;

  /// Product name
  @NotNull @NonNull private String name;

  /// Entity creation timestamp
  @CreationTimestamp
  @Column(updatable = false)
  @ColumnDefault("now()")
  private ZonedDateTime createdAt;

  /// Entity update timestamp
  @UpdateTimestamp
  @ColumnDefault("now()")
  private ZonedDateTime updatedAt;

  /// OrderLines reference
  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  @Exclude
  private List<OrderLine> orderLines;

  /// Category reference
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", referencedColumnName = "id")
  @Exclude
  private Category category;
}
