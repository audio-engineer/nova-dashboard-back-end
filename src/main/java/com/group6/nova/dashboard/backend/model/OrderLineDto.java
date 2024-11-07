package com.group6.nova.dashboard.backend.model;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.NonNull;
import lombok.Value;
import org.springframework.batch.item.file.transform.FieldSet;

/// DTO for the [OrderLine] entity.
///
/// @author Martin Kedmenec
@Value
@SuppressWarnings("ClassWithTooManyFields")
public class OrderLineDto {
  /// `Orderline ID` column
  @SuppressWarnings("PMD.ShortVariable")
  UUID id;

  /// `Product ID` column
  UUID productId;

  /// `Title` column
  String title;

  /// `Quantity` column
  Integer quantity;

  /// `Order ID` column
  UUID orderId;

  /// `Price` column
  BigDecimal price;

  /// `Unit price` column
  BigDecimal unitPrice;

  /// `Unit price discounted` column
  BigDecimal unitPriceDiscounted;

  /// `System product` column
  Boolean systemProduct;

  /// `Quantity Unit` column
  String quantityUnit;

  /// `Cancelled` column
  Boolean cancelled;

  /// Constructor.
  ///
  /// Constructs an [OrderLineDto] instance using data from a [FieldSet].
  ///
  /// @param fieldSet a FieldSet instance provided through `OrderLineFieldSetMapper`
  public OrderLineDto(@NonNull final FieldSet fieldSet) {
    final String parsedOrderLineId = fieldSet.readString(0);
    id = UUID.fromString(parsedOrderLineId);

    final String parsedProductId = fieldSet.readString(1);
    productId = UUID.fromString(parsedProductId);

    title = fieldSet.readString(2);

    quantity = fieldSet.readInt(3);

    final String parsedOrderId = fieldSet.readString(4);
    orderId = UUID.fromString(parsedOrderId);

    final String parsedPrice = fieldSet.readString(5);
    price = BigDecimalParser.parseStringIntoBigDecimal(parsedPrice);

    final String parsedUnitPrice = fieldSet.readString(6);
    unitPrice = BigDecimalParser.parseStringIntoBigDecimal(parsedUnitPrice);

    final String parsedUnitPriceDiscounted = fieldSet.readString(7);
    unitPriceDiscounted = BigDecimalParser.parseStringIntoBigDecimal(parsedUnitPriceDiscounted);

    final int parsedSystemProduct = fieldSet.readInt(8);
    systemProduct = 0 != parsedSystemProduct;

    quantityUnit = fieldSet.readString(9);

    final int parsedCancelled = fieldSet.readInt(10);
    cancelled = 0 != parsedCancelled;
  }
}
