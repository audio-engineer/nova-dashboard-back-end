package com.group6.nova.dashboard.backend;

import static com.group6.nova.dashboard.backend.Constants.FILENAME_ORDERS_CSV;

import com.group6.nova.dashboard.backend.controller.OrderController;
import java.nio.charset.StandardCharsets;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/// Tests of the [OrderController] class.
///
/// @author Martin Kedmenec
/// @see OrderController
@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
@NoArgsConstructor
@ToString
@SuppressWarnings("NestedMethodCall")
class OrderControllerTests {
  ///
  @Autowired private MockMvc mockMvc;

  ///
  @Test
  final void testUpload() throws Exception {
    final MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8);
    final MockMultipartFile user =
        new MockMultipartFile(
            "order",
            FILENAME_ORDERS_CSV,
            MediaType.MULTIPART_FORM_DATA_VALUE,
            "1, 2, 3, 4".getBytes(StandardCharsets.UTF_8));

    mockMvc
        .perform(
            MockMvcRequestBuilders.multipart("/api/orders")
                .file(user)
                .contentType(MediaType.MULTIPART_FORM_DATA))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(textPlainUtf8));
  }
}
