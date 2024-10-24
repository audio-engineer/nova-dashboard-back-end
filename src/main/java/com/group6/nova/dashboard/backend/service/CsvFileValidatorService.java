package com.group6.nova.dashboard.backend.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
import uk.gov.nationalarchives.csv.validator.api.java.CsvValidator;
import uk.gov.nationalarchives.csv.validator.api.java.FailMessage;
import uk.gov.nationalarchives.csv.validator.api.java.Substitution;
import uk.gov.nationalarchives.csv.validator.api.java.WarningMessage;

/// Service class for validating CSV files using a custom schema.
///
/// This service validates CSV files by checking their filename, content type, and content against
/// a specified schema.
/// It uses the [CsvValidator] API to ensure the CSV file conforms to the expected structure defined
/// by the schema.
///
/// Key validation steps:
/// - Checks if the file is empty.
/// - Ensures the content type is "text/csv".
/// - Validates the filename against the expected filename.
/// - Uses a CSV schema to validate the structure and content of the file.
///
/// @author Martin Kedmenec
/// @see CsvValidator
@Slf4j
@ToString
public class CsvFileValidatorService implements Validator {
  /// Allowed filename of the CSV file under test
  private final String allowedFilename;

  /// Filename of a CsvSchema
  private final String schema;

  /// Constructor.
  ///
  /// @param filename the filename of the CSV file to be validated
  /// @param csvSchema the filename of a valid CsvSchema file in the resources directory
  public CsvFileValidatorService(final String filename, final String csvSchema) {
    allowedFilename = filename;
    schema = csvSchema;
  }

  private static boolean isContentTypeValid(final MultipartFile file, final Errors errors) {
    final String contentType = file.getContentType();

    if (!"text/csv".equalsIgnoreCase(contentType)) {
      errors.reject(ValidatorErrorCode.FILE_TYPE);

      return false;
    }

    return true;
  }

  private static boolean isFileEmpty(final MultipartFile file, final Errors errors) {
    if (file.isEmpty()) {
      errors.reject(ValidatorErrorCode.FILE_EMPTY);

      return true;
    }

    return false;
  }

  private boolean isFileNameCorrect(final MultipartFile file, final Errors errors) {
    final String inputFileOriginalFilename = file.getOriginalFilename();

    if (!allowedFilename.equals(inputFileOriginalFilename)) {
      errors.rejectValue(null, ValidatorErrorCode.FILE_NAME);

      return false;
    }

    return true;
  }

  @Override
  public final boolean supports(final Class<?> clazz) {
    return MultipartFile.class.isAssignableFrom(clazz);
  }

  /// {@inheritDoc}
  ///
  /// [SuppressWarnings] explanation:
  /// - `LocalCanBeFinal` - A try-with-resources statement cannot declare final variables.
  @Override
  @SuppressWarnings("LocalCanBeFinal")
  public final void validate(final Object target, final Errors errors) {
    final MultipartFile uploadedFile = (MultipartFile) target;

    if (isFileEmpty(uploadedFile, errors)) {
      return;
    }

    if (!isContentTypeValid(uploadedFile, errors)) {
      return;
    }

    if (!isFileNameCorrect(uploadedFile, errors)) {
      return;
    }

    final List<Substitution> pathSubstitutions = new ArrayList<>(50);
    final List<FailMessage> messages;

    try (InputStream inputFileInputStream = uploadedFile.getInputStream();
        Reader inputFileReader =
            new InputStreamReader(inputFileInputStream, StandardCharsets.UTF_8);
        InputStream orderCsvSchemaInputStream = new ClassPathResource(schema).getInputStream();
        Reader orderCsvSchemaReader =
            new InputStreamReader(orderCsvSchemaInputStream, StandardCharsets.UTF_8)) {
      messages =
          CsvValidator.validate(
              inputFileReader, orderCsvSchemaReader, true, pathSubstitutions, true, false);
    } catch (final IOException exception) {
      throw new UncheckedIOException(exception);
    }

    if (messages.isEmpty()) {
      return;
    }

    final StringBuilder messageStringBuilder = new StringBuilder(16);
    final String lineSeparator = System.lineSeparator();

    for (final FailMessage failMessage : messages) {
      final String message = failMessage.getMessage();

      messageStringBuilder.append(message).append(lineSeparator);

      if (failMessage instanceof WarningMessage) {
        log.warn(message);
      } else { // else is used only to avoid using `continue`
        log.error(message);
      }
    }

    final String messageString = messageStringBuilder.toString();

    errors.rejectValue(null, ValidatorErrorCode.FILE_VALIDATION, messageString);
  }
}
