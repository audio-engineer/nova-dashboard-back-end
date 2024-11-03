package com.group6.nova.dashboard.backend.batchprocessing;

import com.group6.nova.dashboard.backend.annotation.OrderImportJob;
import com.group6.nova.dashboard.backend.annotation.OrderLineAndProductImportJob;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/// Configuration class for setting up Spring Batch jobs for importing orders and order lines.
///
/// @author Martin Kedmenec
@Configuration
@NoArgsConstructor
@ToString
@SuppressWarnings({"DesignForExtension", "DuplicateStringLiteralInspection"})
class JobConfiguration {
  @Bean
  @OrderImportJob
  /* default */ Job orderImportJob(
      final JobRepository jobRepository, @Qualifier("orderImportStep") final Step orderImportStep) {
    return new JobBuilder("orderImportJob", jobRepository).start(orderImportStep).build();
  }

  @Bean
  @OrderLineAndProductImportJob
  /* default */ Job orderLineAndProductImportJob(
      final JobRepository jobRepository,
      @Qualifier("productImportStep") final Step productImportStep,
      @Qualifier("orderLineImportStep") final Step orderLineImportStep) {
    return new JobBuilder("orderLineAndProductImportJob", jobRepository)
        .start(productImportStep)
        .next(orderLineImportStep)
        .build();
  }
}
