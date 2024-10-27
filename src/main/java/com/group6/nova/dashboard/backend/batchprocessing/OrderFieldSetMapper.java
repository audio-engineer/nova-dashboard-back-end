package com.group6.nova.dashboard.backend.batchprocessing;

import com.group6.nova.dashboard.backend.model.Order;
import com.group6.nova.dashboard.backend.model.OrderStatus;
import com.group6.nova.dashboard.backend.model.PaymentStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

/// [FieldSetMapper] implementation for mapping fields from a CSV file to an [Order] entity.
///
/// @author Martin Kedmenec
@Component
@Slf4j
@ToString
public class OrderFieldSetMapper implements FieldSetMapper<Order> {
  /// [DateTimeFormatter] instance
  private final DateTimeFormatter dateTimeFormatter =
      DateTimeFormatter.ofPattern("yyyy-MM-dd[ HH:mm:ss]", Locale.ENGLISH);

  /// [BigDecimalParserService] instance
  private final BigDecimalParserService bigDecimalParserService;

  /// Copenhagen timezone instance
  private final ZoneId copenhagenTimezone = ZoneId.of("UTC+02:00");

  /// Constructor.
  /// @param bigDecimalParserServiceParameter BigDecimalParserService instance
  public OrderFieldSetMapper(final BigDecimalParserService bigDecimalParserServiceParameter) {
    bigDecimalParserService = bigDecimalParserServiceParameter;
  }

  @Override
  public final Order mapFieldSet(final FieldSet fieldSet) {
    final Order order = new Order();

    final String created = fieldSet.readString(0);
    final LocalDateTime parsedCreated = LocalDateTime.parse(created, dateTimeFormatter);
    final ZonedDateTime timezoneCorrectedCreated =
        ZonedDateTime.of(parsedCreated, copenhagenTimezone);
    order.setCreated(timezoneCorrectedCreated);

    final long orderNumber = fieldSet.readLong(1);
    order.setOrderNumber(orderNumber);

    final int orderVatNumber = fieldSet.readInt(2);
    order.setOrderVatNumber(orderVatNumber);

    final String businessDate = fieldSet.readString(3);
    final LocalDate parsedBusinessDate = LocalDate.parse(businessDate, dateTimeFormatter);
    order.setBusinessDate(parsedBusinessDate);

    final String price = fieldSet.readString(4);
    bigDecimalParserService.parseStringIntoBigDecimal(price, order::setPrice);

    final String priceExclVat = fieldSet.readString(5);
    bigDecimalParserService.parseStringIntoBigDecimal(priceExclVat, order::setPriceExclVat);

    final String vat = fieldSet.readString(6);
    bigDecimalParserService.parseStringIntoBigDecimal(vat, order::setVat);

    final String tips = fieldSet.readString(7);
    bigDecimalParserService.parseStringIntoBigDecimal(tips, order::setTips);

    final String paymentStatusString = fieldSet.readString(8);
    final PaymentStatus paymentStatus = PaymentStatus.fromString(paymentStatusString);
    order.setPaymentStatus(paymentStatus);

    final String orderStatusString = fieldSet.readString(9);
    final OrderStatus orderStatus1 = OrderStatus.fromString(orderStatusString);
    order.setOrderStatus(orderStatus1);

    @SuppressWarnings("PMD.LinguisticNaming")
    final int isRevenueInt = fieldSet.readInt(10);
    order.setIsRevenue(0 != isRevenueInt);

    final String orderIdString = fieldSet.readString(11);
    final UUID orderId = UUID.fromString(orderIdString);
    order.setOrderId(orderId);

    final int orderReference = fieldSet.readInt(12, 0);
    order.setOrderReference(orderReference);

    @SuppressWarnings("PMD.LinguisticNaming")
    final int isDemoInt = fieldSet.readInt(13);
    order.setIsDemo(0 != isDemoInt);

    return order;
  }
}
