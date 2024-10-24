package com.group6.nova.dashboard.backend;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.group6.nova.dashboard.backend.service.ValidatorErrorCode;
import java.util.Locale;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Import;

///
@SpringBootTest
@Import(TestcontainersConfiguration.class)
@ToString
@NoArgsConstructor
class MessageSourceTests {
  ///
  @Autowired private MessageSource messageSource;

  @Test
  final void testMessageResolution() {
    final String message =
        messageSource.getMessage(ValidatorErrorCode.FILE_EMPTY, null, Locale.ENGLISH);

    final Matcher<String> matcher = is("File is empty.");

    assertThat("Message should be 'File is empty.'", message, matcher);
  }
}
