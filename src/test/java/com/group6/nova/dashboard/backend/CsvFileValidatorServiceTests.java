package com.group6.nova.dashboard.backend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.group6.nova.dashboard.backend.annotation.OrderValidator;
import com.group6.nova.dashboard.backend.service.ValidatorErrorCode;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
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

///
@SpringBootTest
@Import(TestcontainersConfiguration.class)
@NoArgsConstructor
@ToString
class CsvFileValidatorServiceTests {
  ///
  private static final byte[] EMPTY_FILE = new byte[0];

  ///
  @Mock private Resource orderCsvSchema;

  ///
  @Autowired @OrderValidator private Validator orderValidator;

  ///
  @Autowired private MessageSource messageSource;

  @BeforeEach
  final void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  final void testEmptyFileValidationErrors() {
    final MultipartFile emptyFile =
        new MockMultipartFile(Constants.FILENAME_ORDERS_CSV, EMPTY_FILE);
    final Errors errors = new BeanPropertyBindingResult(emptyFile, "order");

    orderValidator.validate(emptyFile, errors);

    final boolean hasErrors = errors.hasErrors();

    assertThat(hasErrors).isTrue();
  }

  @Test
  final void testEmptyFileValidation() {
    final MultipartFile emptyFile =
        new MockMultipartFile(Constants.FILENAME_ORDERS_CSV, EMPTY_FILE);
    final Errors errors = new BeanPropertyBindingResult(emptyFile, "order");

    orderValidator.validate(emptyFile, errors);

    final String errorCode = errors.getAllErrors().getFirst().getCode();

    assertThat(errorCode).isEqualTo(ValidatorErrorCode.FILE_EMPTY);
  }

  @Test
  final void testInvalidFileTypeValidationErrors() {
    final MultipartFile invalidFile =
        new MockMultipartFile(
            Constants.FILENAME_ORDERS_CSV,
            "test.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "Some content".getBytes(StandardCharsets.UTF_8));
    final Errors errors = new BeanPropertyBindingResult(invalidFile, "order");

    orderValidator.validate(invalidFile, errors);

    final boolean hasErrors = errors.hasErrors();

    assertThat(hasErrors).isTrue();
  }

  @Test
  final void testInvalidFileTypeValidation() {
    final MultipartFile invalidFile =
        new MockMultipartFile(
            Constants.FILENAME_ORDERS_CSV,
            "test.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "Some content".getBytes(StandardCharsets.UTF_8));
    final Errors errors = new BeanPropertyBindingResult(invalidFile, "order");

    orderValidator.validate(invalidFile, errors);

    final String errorCode = errors.getAllErrors().getFirst().getCode();

    assertThat(errorCode).isEqualTo(ValidatorErrorCode.FILE_TYPE);
  }

  @Test
  final void testValidCsvFileValidation() throws IOException {
    final ClassLoader classLoader = getClass().getClassLoader();
    final File file = new File(classLoader.getResource(Constants.FILENAME_ORDERS_CSV).getFile());
    final byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));

    final MultipartFile validFile =
        new MockMultipartFile(
            Constants.FILENAME_ORDERS_CSV,
            Constants.FILENAME_ORDERS_CSV,
            MediaType.MULTIPART_FORM_DATA_VALUE,
            bytes);

    final InputStream schemaInputStream =
        new ByteArrayInputStream("header1,header2".getBytes(StandardCharsets.UTF_8));
    when(orderCsvSchema.getInputStream()).thenReturn(schemaInputStream);

    final Errors errors = new BeanPropertyBindingResult(validFile, "order");

    orderValidator.validate(validFile, errors);

    final boolean hasErrors = errors.hasErrors();

    assertThat(hasErrors).isTrue();
  }

  @Test
  final void testFileWithValidationErrors() throws IOException {
    final MultipartFile invalidFile =
        new MockMultipartFile(
            "file",
            Constants.FILENAME_ORDERS_CSV,
            MediaType.MULTIPART_FORM_DATA_VALUE,
            "invalid,value".getBytes(StandardCharsets.UTF_8));

    final Errors errors = new BeanPropertyBindingResult(invalidFile, "order");

    orderValidator.validate(invalidFile, errors);

    final boolean hasErrors = errors.hasErrors();

    assertThat(hasErrors).isTrue();
  }
}
