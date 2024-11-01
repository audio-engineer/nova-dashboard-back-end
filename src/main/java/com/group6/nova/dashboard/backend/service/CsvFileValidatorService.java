package com.group6.nova.dashboard.backend.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.Errors;
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
/// @see CsvFileValidator
@Slf4j
@ToString
public class CsvFileValidatorService implements CsvFileValidator {
  /// [Collator] instance
  private static final Collator COLLATOR = Collator.getInstance(Locale.US);

  /// Filename prefix of the CSV file under test
  @Getter private final String supportedFilePrefix;

  /// Filename of a CsvSchema
  private final String schema;

  /// Constructor.
  ///
  /// @param filename the filename of the CSV file to be validated
  /// @param csvSchema the filename of a valid CsvSchema file in the resources directory
  public CsvFileValidatorService(final SupportedFiles filename, final String csvSchema) {
    supportedFilePrefix = filename.getFileNamePrefix();
    schema = csvSchema;
  }

  private static boolean isContentTypeValid(final MultipartFile file, final Errors errors) {
    final String contentType = file.getContentType();

    if (0 != COLLATOR.compare("text/csv", contentType)) {
      final String errorCode = CsvFileValidatorErrorCode.FILE_TYPE.getErrorCode();

      errors.reject(errorCode);

      return false;
    }

    return true;
  }

  private static boolean isFileEmpty(final MultipartFile file, final Errors errors) {
    if (file.isEmpty()) {
      final String errorCode = CsvFileValidatorErrorCode.FILE_EMPTY.getErrorCode();

      errors.reject(errorCode);

      return true;
    }

    return false;
  }

  private boolean isFileNameCorrect(final MultipartFile file, final Errors errors) {
    final String inputFileOriginalFilename = file.getOriginalFilename();

    if (null != inputFileOriginalFilename
        && !inputFileOriginalFilename.contains(supportedFilePrefix)) {
      final String errorCode = CsvFileValidatorErrorCode.FILE_NAME.getErrorCode();

      errors.reject(errorCode);

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
  /// This method was split into multiple private methods to avoid a high cyclomatic complexity.
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
      final String message = exception.getMessage();

      log.error(message);

      final String errorCode = CsvFileValidatorErrorCode.FILE_ERROR.getErrorCode();

      errors.reject(errorCode);

      return;
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
    final String errorCode = CsvFileValidatorErrorCode.FILE_VALIDATION.getErrorCode();

    errors.reject(errorCode, messageString);
  }
}
