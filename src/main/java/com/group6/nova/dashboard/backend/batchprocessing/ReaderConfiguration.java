package com.group6.nova.dashboard.backend.batchprocessing;

import com.group6.nova.dashboard.backend.model.OrderDto;
import com.group6.nova.dashboard.backend.model.OrderLineDto;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

/// Configuration class for setting up Spring Batch readers for importing orders and order lines.
///
/// @author Martin Kedmenec
@Configuration
@NoArgsConstructor
@ToString
@SuppressWarnings({"DesignForExtension", "DuplicateStringLiteralInspection"})
class ReaderConfiguration {
  /// File path to uploaded file job parameter
  private static final String JOB_PARAMETERS_FILE_PATH = "#{jobParameters['filePath']}";

  @StepScope
  @Bean
  /* default */ FlatFileItemReader<OrderDto> ordersReader(
      @Value(JOB_PARAMETERS_FILE_PATH) final String filePath,
      final FieldSetMapper<OrderDto> orderFieldSetMapper) {
    final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

    lineTokenizer.setNames(
        "created",
        "order_number",
        "order_vat_number",
        "business_date",
        "price",
        "price_excl_vat",
        "vat",
        "tips",
        "payment_status",
        "order_status",
        "is_revenue",
        "order_id",
        "order_reference",
        "is_demo");

    return new FlatFileItemReaderBuilder<OrderDto>()
        .name("ordersReader")
        .resource(new FileSystemResource(filePath))
        .lineTokenizer(lineTokenizer)
        .fieldSetMapper(orderFieldSetMapper)
        .linesToSkip(1)
        .build();
  }

  @StepScope
  @Bean
  /* default */ FlatFileItemReader<OrderLineDto> orderLinesReader(
      @Value(JOB_PARAMETERS_FILE_PATH) final String filePath,
      final FieldSetMapper<OrderLineDto> orderLineFieldSetMapper) {
    final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

    lineTokenizer.setNames(
        "orderline_id",
        "product_id",
        "title",
        "quantity",
        "order_id",
        "price",
        "unit_price",
        "unit_price_discounted",
        "system_product",
        "quantity_unit",
        "cancelled");

    return new FlatFileItemReaderBuilder<OrderLineDto>()
        .name("orderLinesReader")
        .resource(new FileSystemResource(filePath))
        .lineTokenizer(lineTokenizer)
        .fieldSetMapper(orderLineFieldSetMapper)
        .linesToSkip(1)
        .build();
  }
}
