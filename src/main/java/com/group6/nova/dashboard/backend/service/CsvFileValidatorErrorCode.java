package com.group6.nova.dashboard.backend.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/// Enum containing error codes for file validation errors.
///
/// These constants represent different validation error types used when processing and validating
/// CSV files.
///
/// @author Martin Kedmenec
/// @see CsvFileValidatorService
@AllArgsConstructor
@Getter
@ToString
public enum CsvFileValidatorErrorCode {
  /// Error code indicating the file is empty.
  FILE_EMPTY("file.empty"),

  /// Error code indicating an issue reading the file's content.
  FILE_ERROR("file.error"),

  /// Error code indicating an invalid file type.
  FILE_TYPE("file.type"),

  /// Error code indicating the file name does not match the expected name.
  FILE_NAME("file.name"),

  /// Error code indicating a validation error based on CSV schema validation.
  FILE_VALIDATION("file.validation");

  /// The error code.
  private final String errorCode;
}
