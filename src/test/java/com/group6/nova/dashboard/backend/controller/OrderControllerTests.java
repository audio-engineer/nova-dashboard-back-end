package com.group6.nova.dashboard.backend.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.group6.nova.dashboard.backend.TestcontainersConfiguration;
import com.group6.nova.dashboard.backend.service.SupportedFiles;
import java.nio.file.Files;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
  /// [MockMvc] instance
  @Autowired private MockMvc mockMvc;

  /// `orders.csv` file
  @Value("classpath:orders.csv")
  private Resource resource;

  @Test
  @SuppressWarnings("PMD.LawOfDemeter")
  final void testUploadValidFileShouldReturnOk() throws Exception {
    final byte[] ordersCsvFile = Files.readAllBytes(resource.getFile().toPath());

    final MockMultipartFile mockMultipartFile =
        new MockMultipartFile(
            "orders", SupportedFiles.ORDERS_CSV.getFileNamePrefix(), "text/csv", ordersCsvFile);

    final MockHttpServletResponse response =
        mockMvc
            .perform(
                MockMvcRequestBuilders.multipart("/api/orders")
                    .file(mockMultipartFile)
                    .contentType(MediaType.MULTIPART_FORM_DATA))
            .andReturn()
            .getResponse();

    assertThat(response.getStatus()).isEqualTo(200);
  }
}
