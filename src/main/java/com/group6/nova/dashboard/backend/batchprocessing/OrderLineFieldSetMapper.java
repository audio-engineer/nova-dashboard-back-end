package com.group6.nova.dashboard.backend.batchprocessing;

import com.group6.nova.dashboard.backend.model.Order;
import com.group6.nova.dashboard.backend.model.OrderLine;
import com.group6.nova.dashboard.backend.repository.OrderRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.ToString;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

/// [FieldSetMapper] implementation for mapping fields from a CSV file to an [OrderLine] entity.
///
/// This class is responsible for converting the data from a CSV file into an [OrderLine] entity,
/// including resolving relationships between an [OrderLine] and its associated [Order] entity.
///
/// @author Martin Kedmenec
@Component
@ToString
public class OrderLineFieldSetMapper implements FieldSetMapper<OrderLine> {
  /// [OrderRepository] instance
  private final OrderRepository orderRepository;

  /// [BigDecimalParserService] instance
  private final BigDecimalParserService bigDecimalParserService;

  /// Constructor.
  ///
  /// @param orderRepositoryParameter OrderRepository instance
  /// @param bigDecimalParserServiceParameter BigDecimalParserService instance
  public OrderLineFieldSetMapper(
      final OrderRepository orderRepositoryParameter,
      final BigDecimalParserService bigDecimalParserServiceParameter) {
    orderRepository = orderRepositoryParameter;
    bigDecimalParserService = bigDecimalParserServiceParameter;
  }

  @Override
  public final OrderLine mapFieldSet(final FieldSet fieldSet) {
    final OrderLine orderLine = new OrderLine();

    final String orderLineIdString = fieldSet.readString(0);
    final UUID orderLineId = UUID.fromString(orderLineIdString);
    orderLine.setOrderLineId(orderLineId);

    final String productIdString = fieldSet.readString(1);
    final UUID productId = UUID.fromString(productIdString);
    orderLine.setProductId(productId);

    final String title = fieldSet.readString(2);
    orderLine.setTitle(title);

    final int quantity = fieldSet.readInt(3);
    orderLine.setQuantity(quantity);

    final String orderIdString = fieldSet.readString(4);
    final UUID orderId = UUID.fromString(orderIdString);
    final Optional<Order> order = orderRepository.findByOrderId(orderId);
    orderLine.setOrder(order.orElseThrow(() -> new IllegalArgumentException("Order not found")));

    final String price = fieldSet.readString(5);
    bigDecimalParserService.parseStringIntoBigDecimal(price, orderLine::setPrice);

    final String unitPrice = fieldSet.readString(6);
    bigDecimalParserService.parseStringIntoBigDecimal(unitPrice, orderLine::setUnitPrice);

    final String unitPriceDiscounted = fieldSet.readString(7);
    bigDecimalParserService.parseStringIntoBigDecimal(
        unitPriceDiscounted, orderLine::setUnitPriceDiscounted);

    final int systemProductInt = fieldSet.readInt(8);
    orderLine.setSystemProduct(0 != systemProductInt);

    final String quantityUnit = fieldSet.readString(9);
    orderLine.setQuantityUnit(quantityUnit);

    final int cancelledInt = fieldSet.readInt(10);
    orderLine.setCancelled(0 != cancelledInt);

    return orderLine;
  }
}
