package com.group6.nova.dashboard.backend;

import static org.assertj.core.api.Assertions.assertThat;

import com.group6.nova.dashboard.backend.service.CsvFileValidatorErrorCode;
import java.util.Locale;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Import;

/// Tests of the error message resolution.
///
/// @author Martin Kedmenec
@SpringBootTest
@Import(TestcontainersConfiguration.class)
@NoArgsConstructor
@ToString
@SuppressWarnings("NestedMethodCall")
class MessageSourceTests {
  /// [MessageSource] instance
  @Autowired private MessageSource messageSource;

  @Test
  final void testMessageResolution() {
    final String message =
        messageSource.getMessage(
            CsvFileValidatorErrorCode.FILE_EMPTY.getErrorCode(), null, Locale.ENGLISH);

    assertThat(message).isEqualTo("File is empty.");
  }
}
