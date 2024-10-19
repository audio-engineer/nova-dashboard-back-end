package com.group6.nova.dashboard.backend.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
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
@RequiredArgsConstructor
@ToString
public class ImportController {

  private final JobLauncher jobLauncher;

  private final Job job;

  /// Handles the CSV file upload.
  /// @param file the CSV file to be uploaded
  /// @return a message indicating success
  @PostMapping("/csv-upload")
  public final String handleFileUpload(@RequestParam("csv-file") final MultipartFile file) {
    log.info("CSV file upload started");

    final long parameter = System.currentTimeMillis();

    final JobParameters jobParameters = new JobParametersBuilder()
        .addLong("startAt", parameter)
        .toJobParameters();

    try {
      jobLauncher.run(job, jobParameters);
    } catch (JobExecutionAlreadyRunningException e) {
      log.error("Csv process handler already running!");
    } catch (JobRestartException e) {
      log.error("Could not restart batch job!");
    } catch (JobInstanceAlreadyCompleteException e) {
      log.error("Batch job already complete!");
    } catch (JobParametersInvalidException e) {
      log.error("Invalid batch job!");
    }

    return "You have uploaded " + file.getOriginalFilename() + "!";
  }
}
