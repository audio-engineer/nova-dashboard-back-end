package com.group6.nova.dashboard.backend.controller;

import com.group6.nova.dashboard.backend.annotation.OrderImportJob;
import com.group6.nova.dashboard.backend.annotation.OrderLineImportJob;
import com.group6.nova.dashboard.backend.annotation.OrderLineValidator;
import com.group6.nova.dashboard.backend.annotation.OrderValidator;
import com.group6.nova.dashboard.backend.service.ImportJobService;
import jakarta.validation.Valid;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
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
  private final Validator orderValidator;

  /// orderLineValidator bean
  private final Validator orderLineValidator;

  /// orderImportJob bean
  private final Job orderImportJob;

  /// orderLineImportJob bean
  private final Job orderLineImportJob;

  /// [ImportJobService] instance
  private final ImportJobService importJobService;

  /// Constructor.
  ///
  /// @param orderValidatorParameter orderValidator bean
  /// @param orderLineValidatorParameter orderLineValidator bean
  /// @param orderImportJobParameter orderImportJob bean
  /// @param orderLineImportJobParameter orderLineImportJob bean
  /// @param importJobServiceParameter ImportJobService instance
  public OrderController(
      @OrderValidator final Validator orderValidatorParameter,
      @OrderLineValidator final Validator orderLineValidatorParameter,
      @OrderImportJob final Job orderImportJobParameter,
      @OrderLineImportJob final Job orderLineImportJobParameter,
      final ImportJobService importJobServiceParameter) {
    orderValidator = orderValidatorParameter;
    orderLineValidator = orderLineValidatorParameter;
    orderImportJob = orderImportJobParameter;
    orderLineImportJob = orderLineImportJobParameter;
    importJobService = importJobServiceParameter;
  }

  private ResponseEntity<String> validateAndGenerateResponse(
      final MultipartFile multipartFile,
      final Errors bindingResult,
      final Validator validator,
      final Job job)
      throws ValidationException {
    validator.validate(multipartFile, bindingResult);

    if (bindingResult.hasErrors()) {
      final String message = bindingResult.getAllErrors().getFirst().getDefaultMessage();

      throw new ValidationException(message);
    }

    importJobService.launchImportJob(job, multipartFile);

    return new ResponseEntity<>(UPLOAD_REQUESTED_BODY, HttpStatus.OK);
  }

  /// Handles the upload of `orders.csv`
  ///
  /// @param multipartFile the CSV file to be uploaded
  /// @return a message indicating that the upload started
  /// @throws ValidationException on validation errors
  @PostMapping(value = "/orders", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public final ResponseEntity<String> orderHandler(
      @Valid @RequestParam("orders") final MultipartFile multipartFile) throws ValidationException {
    final Errors bindingResult = new BeanPropertyBindingResult(multipartFile, "order");

    return validateAndGenerateResponse(
        multipartFile, bindingResult, orderValidator, orderImportJob);
  }

  /// Handles the upload of `order-lines.csv`.
  ///
  /// @param multipartFile the CSV file to be uploaded
  /// @return a message indicating that the upload started
  /// @throws ValidationException on validation errors
  @PostMapping(value = "/order-lines", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public final ResponseEntity<String> orderLinesHandler(
      @Valid @RequestParam("order-lines") final MultipartFile multipartFile)
      throws ValidationException {
    final Errors bindingResult = new BeanPropertyBindingResult(multipartFile, "order-line");

    return validateAndGenerateResponse(
        multipartFile, bindingResult, orderLineValidator, orderLineImportJob);
  }
}
