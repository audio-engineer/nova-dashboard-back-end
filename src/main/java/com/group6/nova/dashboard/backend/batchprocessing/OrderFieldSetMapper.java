package com.group6.nova.dashboard.backend.batchprocessing;

import com.group6.nova.dashboard.backend.model.OrderDto;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

/// [FieldSetMapper] implementation for mapping fields from a CSV file to an [OrderDto] entity.
///
/// @author Martin Kedmenec
@Component
@NoArgsConstructor
@ToString
class OrderFieldSetMapper implements FieldSetMapper<OrderDto> {
  @Override
  public final @NonNull OrderDto mapFieldSet(@NonNull final FieldSet fieldSet) {
    return new OrderDto(fieldSet);
  }
}
