package com.group6.nova.dashboard.backend.service;

import org.springframework.validation.Validator;

/// Interface for validating CSV files with a specified filename prefix, extending the standard
/// validation mechanism.
/// Implementations, like [CsvFileValidatorService], validate the file structure and content against
/// an expected schema.
///
/// @author Martin Kedmenec
/// @see Validator
/// @see CsvFileValidatorService
public interface CsvFileValidator extends Validator {
  /// Gets the filename prefix of the file.
  ///
  /// @return the filename prefix of the file
  String getSupportedFilePrefix();
}
