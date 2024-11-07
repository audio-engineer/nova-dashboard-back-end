package com.group6.nova.dashboard.backend.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;
import lombok.NonNull;
import lombok.Value;
import org.springframework.batch.item.file.transform.FieldSet;

/// DTO for the [Order] entity.
///
/// @author Martin Kedmenec
@Value
@SuppressWarnings("ClassWithTooManyFields")
public class OrderDto {
  /// [DateTimeFormatter] instance
  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd[ HH:mm:ss]", Locale.ENGLISH);

  /// Copenhagen timezone instance
  private static final ZoneId COPENHAGEN_TIMEZONE = ZoneId.of("UTC+02:00");

  /// `Created` column
  ZonedDateTime created;

  /// `Order Number` column
  Long orderNumber;

  /// `OrderVAT Number` column
  Integer orderVatNumber;

  /// `Business Date` column
  LocalDate businessDate;

  /// `Price` column
  BigDecimal price;

  /// `Price excl. VAT` column
  BigDecimal priceExclVat;

  /// `VAT` column
  BigDecimal vat;

  /// `Tips` column
  BigDecimal tips;

  /// `Payment Status` column
  PaymentStatus paymentStatus;

  /// `Order Status` column
  OrderStatus orderStatus;

  /// `Is revenue` column
  Boolean isRevenue;

  /// `Order ID` column
  @SuppressWarnings("PMD.ShortVariable")
  UUID id;

  /// `Order Reference` column
  Integer orderReference;

  /// `isDemo` column
  Boolean isDemo;

  /// Constructor.
  ///
  /// Constructs an [OrderDto] instance using data from a [FieldSet].
  ///
  /// @param fieldSet a FieldSet instance provided through `OrderFieldSetMapper`
  public OrderDto(@NonNull final FieldSet fieldSet) {
    final String parsedCreated = fieldSet.readString(0);
    final LocalDateTime localDateTimeCreated =
        LocalDateTime.parse(parsedCreated, DATE_TIME_FORMATTER);
    created = ZonedDateTime.of(localDateTimeCreated, COPENHAGEN_TIMEZONE);

    orderNumber = fieldSet.readLong(1);

    orderVatNumber = fieldSet.readInt(2);

    final String parsedBusinessDate = fieldSet.readString(3);
    businessDate = LocalDate.parse(parsedBusinessDate, DATE_TIME_FORMATTER);

    final String parsedPrice = fieldSet.readString(4);
    price = BigDecimalParser.parseStringIntoBigDecimal(parsedPrice);

    final String parsedPriceExclVat = fieldSet.readString(5);
    priceExclVat = BigDecimalParser.parseStringIntoBigDecimal(parsedPriceExclVat);

    final String parsedVat = fieldSet.readString(6);
    vat = BigDecimalParser.parseStringIntoBigDecimal(parsedVat);

    final String parsedTips = fieldSet.readString(7);
    tips = BigDecimalParser.parseStringIntoBigDecimal(parsedTips);

    final String parsedPaymentStatus = fieldSet.readString(8);
    paymentStatus = PaymentStatus.fromString(parsedPaymentStatus);

    final String parsedOrderStatus = fieldSet.readString(9);
    orderStatus = OrderStatus.fromString(parsedOrderStatus);

    final int parsedIsRevenue = fieldSet.readInt(10);
    isRevenue = 0 != parsedIsRevenue;

    final String parsedOrderId = fieldSet.readString(11);
    id = UUID.fromString(parsedOrderId);

    orderReference = fieldSet.readInt(12, 0);

    final int parsedIsDemo = fieldSet.readInt(13);
    isDemo = 0 != parsedIsDemo;
  }
}
