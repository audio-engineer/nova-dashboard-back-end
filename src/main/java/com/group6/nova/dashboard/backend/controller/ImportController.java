package com.group6.nova.dashboard.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/// Controller that handles file upload actions, specifically for CSV files.
///
/// Provides an endpoint to upload CSV files, logging the upload process and returning a message
///  indicating the result.
///
/// Annotations:
/// - [Slf4j] - Enables logging within the class.
/// - [RestController] - Marks the class as a REST controller.
/// - [RequestMapping] - Maps the root path for this controller to `/import`.
///
/// @author Martin Kedmenec
/// @see MultipartFile
@Slf4j
@RestController
@RequestMapping("import")
public class ImportController {
  /// Constructor.
  public ImportController() {
    //
  }

  /// Handles the CSV file upload.
  /// @param file the CSV file to be uploaded
  /// @return a message indicating success
  @PostMapping("/csv-upload")
  public final String handleFileUpload(@RequestParam("csv-file") final MultipartFile file) {
    log.info("CSV file upload started");

    return "You have uploaded " + file.getOriginalFilename() + "!";
  }
}
