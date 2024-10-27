package com.group6.nova.dashboard.backend.batchprocessing;

import com.group6.nova.dashboard.backend.annotation.OrderImportJob;
import com.group6.nova.dashboard.backend.annotation.OrderLineImportJob;
import com.group6.nova.dashboard.backend.model.Order;
import com.group6.nova.dashboard.backend.model.OrderLine;
import com.group6.nova.dashboard.backend.repository.OrderLineRepository;
import com.group6.nova.dashboard.backend.repository.OrderRepository;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

/// Configuration class for setting up Spring Batch jobs and steps for importing orders and order
/// lines.
///
/// @author Martin Kedmenec
/// @see FlatFileItemReader
/// @see RepositoryItemWriter
/// @see Step
/// @see Job
@Configuration
@NoArgsConstructor
@ToString
@SuppressWarnings("DesignForExtension")
class BatchConfiguration {
  @StepScope
  @Bean
  /* default */ FlatFileItemReader<Order> ordersReader(
      @Value("#{jobParameters['filePath']}") final String filePath,
      final FieldSetMapper<Order> orderFieldSetMapper) {
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

    return new FlatFileItemReaderBuilder<Order>()
        .name("ordersReader")
        .resource(new FileSystemResource(filePath))
        .lineTokenizer(lineTokenizer)
        .fieldSetMapper(orderFieldSetMapper)
        .linesToSkip(1)
        .build();
  }

  @StepScope
  @Bean
  /* default */ FlatFileItemReader<OrderLine> orderLinesReader(
      @Value("#{jobParameters['filePath']}") final String filePath,
      final FieldSetMapper<OrderLine> orderLineFieldSetMapper) {
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

    return new FlatFileItemReaderBuilder<OrderLine>()
        .name("orderLinesReader")
        .resource(new FileSystemResource(filePath))
        .lineTokenizer(lineTokenizer)
        .fieldSetMapper(orderLineFieldSetMapper)
        .linesToSkip(1)
        .build();
  }

  @Bean
  /* default */ RepositoryItemWriter<Order> orderRepositoryWriter(
      final OrderRepository orderRepository) {
    final RepositoryItemWriter<Order> writer = new RepositoryItemWriter<>();

    writer.setRepository(orderRepository);
    writer.setMethodName("save");

    return writer;
  }

  @Bean
  /* default */ RepositoryItemWriter<OrderLine> orderLineRepositoryWriter(
      final OrderLineRepository orderLineRepository) {
    final RepositoryItemWriter<OrderLine> writer = new RepositoryItemWriter<>();

    writer.setRepository(orderLineRepository);
    writer.setMethodName("save");

    return writer;
  }

  @Bean
  /* default */ Step orderImportStep(
      final JobRepository jobRepository,
      final PlatformTransactionManager platformTransactionManager,
      @Qualifier("ordersReader") final FlatFileItemReader<? extends Order> reader,
      @Qualifier("orderRepositoryWriter") final RepositoryItemWriter<? super Order> writer) {

    return new StepBuilder("orderImportStep", jobRepository)
        .<Order, Order>chunk(3, platformTransactionManager)
        .reader(reader)
        .writer(writer)
        .build();
  }

  @Bean
  /* default */ Step orderLineImportStep(
      final JobRepository jobRepository,
      final PlatformTransactionManager platformTransactionManager,
      @Qualifier("orderLinesReader") final FlatFileItemReader<? extends OrderLine> reader,
      @Qualifier("orderLineRepositoryWriter")
          final RepositoryItemWriter<? super OrderLine> writer) {

    return new StepBuilder("orderLineImportStep", jobRepository)
        .<OrderLine, OrderLine>chunk(3, platformTransactionManager)
        .reader(reader)
        .writer(writer)
        .build();
  }

  @Bean
  @OrderImportJob
  /* default */ Job orderImportJob(
      final JobRepository jobRepository, @Qualifier("orderImportStep") final Step orderImportStep) {
    return new JobBuilder("orderImportJob", jobRepository).start(orderImportStep).build();
  }

  @Bean
  @OrderLineImportJob
  /* default */ Job orderLineImportJob(
      final JobRepository jobRepository,
      @Qualifier("orderLineImportStep") final Step orderLineImportStep) {
    return new JobBuilder("orderLineImportJob", jobRepository).start(orderLineImportStep).build();
  }
}
