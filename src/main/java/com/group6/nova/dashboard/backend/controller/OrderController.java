package com.group6.nova.dashboard.backend.controller;

import com.group6.nova.dashboard.backend.annotation.OrderImportJob;
import com.group6.nova.dashboard.backend.annotation.OrderLineAndProductImportJob;
import com.group6.nova.dashboard.backend.annotation.OrderLineValidator;
import com.group6.nova.dashboard.backend.annotation.OrderValidator;
import com.group6.nova.dashboard.backend.service.CsvFileValidator;
import com.group6.nova.dashboard.backend.service.ImportJob;
import jakarta.validation.Valid;
import java.util.Locale;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/// Controller that handles file upload actions, specifically for CSV files.
///
/// Provides an endpoint to upload CSV files, logging the upload process and returning a message
///  indicating the result.
///
/// @author Martin Kedmenec
/// @see MultipartFile
@RestController
@RequestMapping("/api")
@Slf4j
@ToString
public class OrderController {
  /// Default OK response message
  private static final String UPLOAD_REQUESTED_BODY = "Upload requested";

  /// orderValidator bean
  private final CsvFileValidator orderValidator;

  /// orderLineValidator bean
  private final CsvFileValidator orderLineValidator;

  /// orderImportJob bean
  private final Job orderImportJob;

  /// orderLineImportJob bean
  private final Job orderLineAndProductImportJob;

  /// [ImportJob] instance
  private final ImportJob importJob;

  /// [MessageSource] instance
  private final MessageSource messageSource;

  /// Constructor.
  ///
  /// @param orderValidatorParameter orderValidator bean
  /// @param orderLineValidatorParameter orderLineValidator bean
  /// @param orderImportJobParameter orderImportJob bean
  /// @param orderLineAndProductImportJobParameter orderLineImportJob bean
  /// @param importJobParameter ImportJobService instance
  /// @param messageSourceParameter MessageSource instance
  public OrderController(
      @OrderValidator final CsvFileValidator orderValidatorParameter,
      @OrderLineValidator final CsvFileValidator orderLineValidatorParameter,
      @OrderImportJob final Job orderImportJobParameter,
      @OrderLineAndProductImportJob final Job orderLineAndProductImportJobParameter,
      final ImportJob importJobParameter,
      final MessageSource messageSourceParameter) {
    orderValidator = orderValidatorParameter;
    orderLineValidator = orderLineValidatorParameter;
    orderImportJob = orderImportJobParameter;
    orderLineAndProductImportJob = orderLineAndProductImportJobParameter;
    importJob = importJobParameter;
    messageSource = messageSourceParameter;
  }

  private ResponseEntity<String> validateAndGenerateResponse(
      final MultipartFile multipartFile,
      final Errors bindingResult,
      final CsvFileValidator validator,
      final Job job) {
    validator.validate(multipartFile, bindingResult);

    if (bindingResult.hasErrors()) {
      String errorCode = bindingResult.getAllErrors().getFirst().getCode();

      if (null == errorCode) {
        errorCode = "error.null";
      }

      final String supportedFilePrefix = validator.getSupportedFilePrefix();
      final String message =
          messageSource.getMessage(errorCode, new Object[] {supportedFilePrefix}, Locale.ENGLISH);

      throw new ValidationException(message);
    }

    importJob.launchImportJob(job, multipartFile);

    return new ResponseEntity<>(UPLOAD_REQUESTED_BODY, HttpStatus.OK);
  }

  /// Handles the upload of the `orders_*` CSV file.
  ///
  /// @param multipartFile the CSV file to be uploaded
  /// @return a message indicating that the upload started
  /// @throws ValidationException on validation errors
  @PostMapping(value = "/orders", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public final ResponseEntity<String> orderHandler(
      @Valid @RequestParam("orders") final MultipartFile multipartFile) {
    final Errors bindingResult = new BeanPropertyBindingResult(multipartFile, "order");

    return validateAndGenerateResponse(
        multipartFile, bindingResult, orderValidator, orderImportJob);
  }

  /// Handles the upload of the `orderlines_*` CSV file.
  ///
  /// @param multipartFile the CSV file to be uploaded
  /// @return a message indicating that the upload started
  /// @throws ValidationException on validation errors
  @PostMapping(value = "/order-lines", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public final ResponseEntity<String> orderLinesHandler(
      @Valid @RequestParam("order-lines") final MultipartFile multipartFile) {
    final Errors bindingResult = new BeanPropertyBindingResult(multipartFile, "orderLine");

    return validateAndGenerateResponse(
        multipartFile, bindingResult, orderLineValidator, orderLineAndProductImportJob);
  }
}
