package com.group6.novadashboardbackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/** Handles CSV file upload actions. */
@Controller
public class CsvFileUploadController {
  /** The default CsvFileUploadController constructor. */
  public CsvFileUploadController() {
    //
  }

  /**
   * Handles the CSV file upload.
   *
   * @param file The CSV file.
   * @return A redirect.
   */
  @PostMapping("/csv-upload")
  @ResponseBody
  public String handleFileUpload(final @RequestParam("csv-file") MultipartFile file) {
    return "You have uploaded " + file.getOriginalFilename() + "!";
  }
}
