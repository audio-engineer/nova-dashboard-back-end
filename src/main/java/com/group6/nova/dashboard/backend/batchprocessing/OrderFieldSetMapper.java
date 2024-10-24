package com.group6.nova.dashboard.backend.batchprocessing;

import com.group6.nova.dashboard.backend.model.Order;
import com.group6.nova.dashboard.backend.model.OrderStatus;
import com.group6.nova.dashboard.backend.model.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;
import lombok.ToString;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

/// [FieldSetMapper] implementation for mapping fields from a CSV file to an [Order] entity.
///
/// @author Martin Kedmenec
@Component
@ToString
public class OrderFieldSetMapper implements FieldSetMapper<Order> {
  /// `DateTimeFormatter` instance
  private final DateTimeFormatter dateTimeFormatter =
      DateTimeFormatter.ofPattern("M/d/yy[ H:mm]", Locale.ENGLISH);

  /// Copenhagen timezone instance
  private final ZoneId copenhagenTimezone = ZoneId.of("UTC+02:00");

  /// Constructor.
  public OrderFieldSetMapper() {
    //
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
    parsedBusinessDate.atStartOfDay(copenhagenTimezone);
    order.setBusinessDate(parsedBusinessDate);

    final BigDecimal price = fieldSet.readBigDecimal(4);
    order.setPrice(price);

    final BigDecimal priceExclVat = fieldSet.readBigDecimal(5);
    order.setPriceExclVat(priceExclVat);

    final BigDecimal vat = fieldSet.readBigDecimal(6);
    order.setVat(vat);

    final BigDecimal tips = fieldSet.readBigDecimal(7);
    order.setTips(tips);

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
