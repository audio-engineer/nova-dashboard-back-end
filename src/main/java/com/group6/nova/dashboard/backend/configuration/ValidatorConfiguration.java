package com.group6.nova.dashboard.backend.configuration;

import com.group6.nova.dashboard.backend.Constants;
import com.group6.nova.dashboard.backend.WarningValue;
import com.group6.nova.dashboard.backend.annotation.OrderLineValidator;
import com.group6.nova.dashboard.backend.annotation.OrderValidator;
import com.group6.nova.dashboard.backend.service.CsvFileValidatorService;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;

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
  /* default */ Validator orderValidator() {
    return new CsvFileValidatorService(Constants.FILENAME_ORDERS_CSV, "orders.csvs");
  }

  @Bean
  @OrderLineValidator
  @SuppressWarnings(WarningValue.DESIGN_FOR_EXTENSION)
  /* default */ Validator orderLineValidator() {
    return new CsvFileValidatorService(Constants.FILENAME_ORDER_LINES_CSV, "order-lines.csvs");
  }
}
