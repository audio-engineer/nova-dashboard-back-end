package com.group6.nova.dashboard.backend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/// Represents the category entity.
///
/// @author Martin Kedmenec
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Category {
  /// Category ID
  @Id
  @GeneratedValue
  @SuppressWarnings("PMD.ShortVariable")
  private Long id;

  /// Category name
  @NotNull private String name;

  /// Entity creation timestamp
  @CreationTimestamp
  @Column(updatable = false)
  @ColumnDefault("now()")
  private ZonedDateTime createdAt;

  /// Entity update timestamp
  @UpdateTimestamp
  @ColumnDefault("now()")
  private ZonedDateTime updatedAt;

  /// Product reference
  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
  @Exclude
  private List<Product> products;
}
