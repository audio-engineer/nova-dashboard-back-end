package com.group6.nova.dashboard.backend.batchprocessing;

import com.group6.nova.dashboard.backend.model.Order;
import com.group6.nova.dashboard.backend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;


/// Represents the configuration of the ItemReader class
@Configuration
@RequiredArgsConstructor
@ToString
public class BatchConfig {

  private final JobRepository jobRepository;
  private final PlatformTransactionManager platformTransactionManager;
  private final OrderRepository orderRepository;

  /// The itemReader for csv files of the order type.
  /// @return ItemReader
  @Bean
  public FlatFileItemReader<Order> orderItemReader() {
    final FlatFileItemReader<Order> itemReader = new FlatFileItemReader<>();
    itemReader.setResource(new FileSystemResource("./nova_orders.csv"));
    itemReader.setName("orderReader");
    itemReader.setLinesToSkip(1);

    final LineMapper<Order> lineMapper = lineMapper();

    itemReader.setLineMapper(lineMapper);
    return itemReader;
  }

  public Step importStep() {
    final FlatFileItemReader<Order> reader = orderItemReader();
    final OrderProcessor processor = processor();
    final RepositoryItemWriter<Order> writer = writer();

    return new StepBuilder("csvOrderImport", jobRepository)
        .<Order, Order>chunk(10, platformTransactionManager)
        .reader(reader)
        .processor(processor)
        .writer(writer)
        .build();
  }

  @Bean
  public Job runJob() {
    final Step step = importStep();

    return new JobBuilder("importOrders", jobRepository)
        .start(step)
        .build();
  }

  private static LineMapper<Order> lineMapper() {
    final DefaultLineMapper<Order> lineMapper = new DefaultLineMapper<>();

    final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
    lineTokenizer.setDelimiter(",");
    lineTokenizer.setStrict(true);
    lineTokenizer.setNames("created", "order_number", "order_vat_number", "business_date",
        "price", "price_excl_vat", "vat", "tips", "payment_status", "order_status",
        "is_revenue", "order_id", "order_reference", "is_demo");

    final BeanWrapperFieldSetMapper<Order> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
    fieldSetMapper.setTargetType(Order.class);

    lineMapper.setLineTokenizer(lineTokenizer);
    lineMapper.setFieldSetMapper(fieldSetMapper);

    return lineMapper;
  }

  @Bean
  public OrderProcessor processor() {
    return new OrderProcessor();
  }

  @Bean
  public RepositoryItemWriter<Order> writer() {
    final RepositoryItemWriter<Order> writer = new RepositoryItemWriter<>();
    writer.setRepository(orderRepository);
    writer.setMethodName("save");
    return writer;
  }
}
