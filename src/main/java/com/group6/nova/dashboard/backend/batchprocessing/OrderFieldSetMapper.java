package com.group6.nova.dashboard.backend.batchprocessing;

import com.group6.nova.dashboard.backend.model.Order;
import com.group6.nova.dashboard.backend.model.OrderDto;
import com.group6.nova.dashboard.backend.model.OrderMapper;
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
  /// [OrderMapper] instance
  private final OrderMapper orderMapper;

  /// Constructor.
  ///
  /// @param orderMapperParameter OrderMapper instance
  public OrderFieldSetMapper(final OrderMapper orderMapperParameter) {
    orderMapper = orderMapperParameter;
  }

  @Override
  public final Order mapFieldSet(final FieldSet fieldSet) {
    final OrderDto orderDto = new OrderDto(fieldSet);

    return orderMapper.toEntity(orderDto);
  }
}
