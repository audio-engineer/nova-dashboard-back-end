package com.group6.nova.dashboard.backend.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.group6.nova.dashboard.backend.TestcontainersConfiguration;
import com.group6.nova.dashboard.backend.annotation.OrderValidator;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

/// Tests of the [CsvFileValidatorService] class.
///
/// @author Martin Kedmenec
/// @see CsvFileValidatorService
@SpringBootTest
@Import(TestcontainersConfiguration.class)
@NoArgsConstructor
@ToString
@SuppressWarnings("NestedMethodCall")
class CsvFileValidatorServiceTests {
  /// Mock empty file
  private static final byte[] EMPTY_FILE = new byte[0];

  /// Used for configuring [BeanPropertyBindingResult]
  private static final String ORDER_TARGET_OBJECT = "order";

  /// `orders.csv` file
  @Value("classpath:orders.csv")
  private Resource resource;

  /// orderValidator bean
  @Autowired @OrderValidator private Validator orderValidator;

  /// [MessageSource] instance
  @Autowired private MessageSource messageSource;

  @BeforeEach
  final void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  final void testEmptyFileValidationErrors() {
    final MultipartFile emptyFile =
        new MockMultipartFile(SupportedFiles.ORDERS_CSV.getFileNamePrefix(), EMPTY_FILE);
    final Errors errors = new BeanPropertyBindingResult(emptyFile, ORDER_TARGET_OBJECT);

    orderValidator.validate(emptyFile, errors);

    final boolean hasErrors = errors.hasErrors();

    assertThat(hasErrors).isTrue();
  }

  @Test
  final void testEmptyFileValidation() {
    final MultipartFile emptyFile =
        new MockMultipartFile(SupportedFiles.ORDERS_CSV.getFileNamePrefix(), EMPTY_FILE);
    final Errors errors = new BeanPropertyBindingResult(emptyFile, ORDER_TARGET_OBJECT);

    orderValidator.validate(emptyFile, errors);

    final String actualErrorCode = errors.getAllErrors().getFirst().getCode();
    final String expectedErrorCode = CsvFileValidatorErrorCode.FILE_EMPTY.getErrorCode();

    assertThat(actualErrorCode).isEqualTo(expectedErrorCode);
  }

  @Test
  final void testInvalidFileTypeValidationErrors() {
    final MultipartFile invalidFile =
        new MockMultipartFile(
            SupportedFiles.ORDERS_CSV.getFileNamePrefix(),
            "test.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "Some content".getBytes(StandardCharsets.UTF_8));
    final Errors errors = new BeanPropertyBindingResult(invalidFile, ORDER_TARGET_OBJECT);

    orderValidator.validate(invalidFile, errors);

    final boolean hasErrors = errors.hasErrors();

    assertThat(hasErrors).isTrue();
  }

  @Test
  final void testInvalidFileTypeValidation() {
    final MultipartFile invalidFile =
        new MockMultipartFile(
            SupportedFiles.ORDERS_CSV.getFileNamePrefix(),
            "test.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "Some content".getBytes(StandardCharsets.UTF_8));
    final Errors errors = new BeanPropertyBindingResult(invalidFile, ORDER_TARGET_OBJECT);

    orderValidator.validate(invalidFile, errors);

    final String actualErrorCode = errors.getAllErrors().getFirst().getCode();
    final String expectedErrorCode = CsvFileValidatorErrorCode.FILE_TYPE.getErrorCode();

    assertThat(actualErrorCode).isEqualTo(expectedErrorCode);
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  @Test
  final void testValidCsvFileValidation() throws IOException {
    final byte[] ordersCsvFile = Files.readAllBytes(resource.getFile().toPath());

    final MultipartFile validFile =
        new MockMultipartFile(
            SupportedFiles.ORDERS_CSV.getFileNamePrefix(),
            SupportedFiles.ORDERS_CSV.getFileNamePrefix(),
            "text/csv",
            ordersCsvFile);

    final Errors errors = new BeanPropertyBindingResult(validFile, ORDER_TARGET_OBJECT);

    orderValidator.validate(validFile, errors);

    final boolean hasErrors = errors.hasErrors();

    assertThat(hasErrors).isFalse();
  }

  @Test
  final void testFileWithValidationErrors() {
    final MultipartFile invalidFile =
        new MockMultipartFile(
            SupportedFiles.ORDERS_CSV.getFileNamePrefix(),
            SupportedFiles.ORDERS_CSV.getFileNamePrefix(),
            "text/csv",
            "invalid,value".getBytes(StandardCharsets.UTF_8));

    final Errors errors = new BeanPropertyBindingResult(invalidFile, ORDER_TARGET_OBJECT);

    orderValidator.validate(invalidFile, errors);

    final boolean hasErrors = errors.hasErrors();

    assertThat(hasErrors).isTrue();
  }
}
