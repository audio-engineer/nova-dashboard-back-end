package com.group6.novadashboardbackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** Handles CSV file upload actions. */
@Controller
public class CsvFileUploadController {
  /** The CsvFileUploadController constructor. */
  public CsvFileUploadController() {
    //
  }

  /**
   * Handles the CSV file upload.
   *
   * @param file The CSV file.
   * @param redirectAttributes Redirect attributes.
   * @return A redirect.
   */
  @PostMapping("/csv-upload")
  public String handleFileUpload(
      final @RequestParam("file") MultipartFile file, final RedirectAttributes redirectAttributes) {

    redirectAttributes.addFlashAttribute(
        "message", "You successfully uploaded " + file.getOriginalFilename() + "!");

    return "redirect:/";
  }
}
