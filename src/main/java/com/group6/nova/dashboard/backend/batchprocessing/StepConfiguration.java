package com.group6.nova.dashboard.backend.batchprocessing;

import com.group6.nova.dashboard.backend.model.Order;
import com.group6.nova.dashboard.backend.model.OrderDto;
import com.group6.nova.dashboard.backend.model.OrderLine;
import com.group6.nova.dashboard.backend.model.OrderLineDto;
import com.group6.nova.dashboard.backend.model.Product;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/// Configuration class for setting up Spring Batch steps for importing orders and order lines.
///
/// @author Martin Kedmenec
@Configuration
@NoArgsConstructor
@ToString
@SuppressWarnings({"DesignForExtension", "DuplicateStringLiteralInspection"})
class StepConfiguration {
  @Bean
  /* default */ Step orderImportStep(
      final JobRepository jobRepository,
      final PlatformTransactionManager platformTransactionManager,
      @Qualifier("ordersReader") final FlatFileItemReader<OrderDto> reader,
      @Qualifier("orderDuplicateItemProcessor")
          final ItemProcessor<? super OrderDto, ? extends Order> processor,
      @Qualifier("orderRepositoryWriter") final RepositoryItemWriter<? super Order> writer) {

    return new StepBuilder("orderImportStep", jobRepository)
        .<OrderDto, Order>chunk(3, platformTransactionManager)
        .reader(reader)
        .processor(processor)
        .writer(writer)
        .build();
  }

  @Bean
  /* default */ Step orderLineImportStep(
      final JobRepository jobRepository,
      final PlatformTransactionManager platformTransactionManager,
      @Qualifier("orderLinesReader") final FlatFileItemReader<OrderLineDto> reader,
      @Qualifier("orderLineDuplicateItemProcessor")
          final ItemProcessor<? super OrderLineDto, ? extends OrderLine> processor,
      @Qualifier("orderLineRepositoryWriter")
          final RepositoryItemWriter<? super OrderLine> writer) {
    return new StepBuilder("orderLineImportStep", jobRepository)
        .<OrderLineDto, OrderLine>chunk(3, platformTransactionManager)
        .reader(reader)
        .processor(processor)
        .writer(writer)
        .build();
  }

  @Bean
  /* default */ Step productImportStep(
      final JobRepository jobRepository,
      final PlatformTransactionManager platformTransactionManager,
      @Qualifier("orderLinesReader") final FlatFileItemReader<OrderLineDto> reader,
      @Qualifier("productDuplicateItemProcessor")
          final ItemProcessor<? super OrderLineDto, ? extends Product> processor,
      @Qualifier("productRepositoryWriter") final RepositoryItemWriter<? super Product> writer) {
    return new StepBuilder("productImportStep", jobRepository)
        .<OrderLineDto, Product>chunk(3, platformTransactionManager)
        .reader(reader)
        .processor(processor)
        .writer(writer)
        .build();
  }
}
