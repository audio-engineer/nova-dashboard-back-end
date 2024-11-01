package com.group6.nova.dashboard.backend.configuration;

import com.group6.nova.dashboard.backend.WarningValue;
import com.group6.nova.dashboard.backend.annotation.OrderLineValidator;
import com.group6.nova.dashboard.backend.annotation.OrderValidator;
import com.group6.nova.dashboard.backend.service.CsvFileValidator;
import com.group6.nova.dashboard.backend.service.CsvFileValidatorService;
import com.group6.nova.dashboard.backend.service.SupportedFiles;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/// Configuration class for setting up custom validators for the application.
///
/// This class defines beans for validating CSV files associated with orders and order lines using
/// the [CsvFileValidatorService].
///
/// @author Martin Kedmenec
@Configuration
@NoArgsConstructor
class ValidatorConfiguration {
  @Bean
  @OrderValidator
  @SuppressWarnings(WarningValue.DESIGN_FOR_EXTENSION)
  /* default */ CsvFileValidator orderValidator() {
    return new CsvFileValidatorService(SupportedFiles.ORDERS_CSV, "orders.csvs");
  }

  @Bean
  @OrderLineValidator
  @SuppressWarnings(WarningValue.DESIGN_FOR_EXTENSION)
  /* default */ CsvFileValidator orderLineValidator() {
    return new CsvFileValidatorService(SupportedFiles.ORDER_LINES_CSV, "order-lines.csvs");
  }
}
