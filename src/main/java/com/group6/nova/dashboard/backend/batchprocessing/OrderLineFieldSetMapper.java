package com.group6.nova.dashboard.backend.batchprocessing;

import com.group6.nova.dashboard.backend.model.OrderLineDto;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

/// [FieldSetMapper] implementation for mapping fields from a CSV file to an [OrderLineDto] entity.
///
/// @author Martin Kedmenec
@Component
@NoArgsConstructor
@ToString
class OrderLineFieldSetMapper implements FieldSetMapper<OrderLineDto> {
  @Override
  public final @NonNull OrderLineDto mapFieldSet(@NonNull final FieldSet fieldSet) {
    return new OrderLineDto(fieldSet);
  }
}
