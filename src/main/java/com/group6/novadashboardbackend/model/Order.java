package com.group6.novadashboardbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

/** Represents the order entity. */
@AllArgsConstructor
@Data
@Entity
@Table(name = "order")
public class Order {
  /** Order ID. */
  @Id private Integer orderId;

  /** Order name. */
  private String name;

  /** Order created at. */
  private LocalDateTime createdAt;

  /** The default Order constructor. */
  public Order() {
    //
  }
}
