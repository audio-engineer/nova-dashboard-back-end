package com.group6.novadashboardbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/// Handles CSV file upload actions.
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
