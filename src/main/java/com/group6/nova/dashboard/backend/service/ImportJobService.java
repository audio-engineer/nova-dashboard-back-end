package com.group6.nova.dashboard.backend.service;

import com.group6.nova.dashboard.backend.WarningValue;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/// Service responsible for executing import various import batch jobs.
///
/// @author Martin Kedmenec
/// @see Job
/// @see JobLauncher
@Service
@Slf4j
@ToString
public class ImportJobService {
  /// [JobLauncher] instance
  private final JobLauncher jobLauncher;

  /// Constructor.
  ///
  /// @param jobLauncherParameter JobLauncher instance
  public ImportJobService(final JobLauncher jobLauncherParameter) {
    jobLauncher = jobLauncherParameter;
  }

  private static String writeFileToTempFile(final MultipartFile multipartFile) throws IOException {
    final File tempFile;

    try {
      tempFile = File.createTempFile("uploaded_", ".csv");
    } catch (final IOException exception) {
      throw new IOException("The temp file could not be created.", exception);
    }

    final String tempFileAbsolutePath = tempFile.getAbsolutePath();
    final Path tempFilePath = Paths.get(tempFileAbsolutePath);

    try (@SuppressWarnings("LocalCanBeFinal")
        OutputStream outputStream = Files.newOutputStream(tempFilePath)) {
      final byte[] bytes = multipartFile.getBytes();

      outputStream.write(bytes);
    } catch (final IOException exception) {
      final String message = exception.getMessage();

      log.error(message, exception);
    }

    return tempFileAbsolutePath;
  }

  /// Saves the multipart file to a temporal file in the filesystem, and, on success, proceeds to
  /// launch the batch job.
  ///
  /// @param job a Job instance
  /// @param multipartFile a MultipartFile instance
  @Async
  @SuppressWarnings(WarningValue.DESIGN_FOR_EXTENSION)
  public void launchImportJob(final Job job, final MultipartFile multipartFile) {
    final String tempFileAbsolutePath;

    try {
      tempFileAbsolutePath = writeFileToTempFile(multipartFile);
    } catch (final IOException exception) {
      final String message = exception.getMessage();

      log.error(message, exception);

      return;
    }

    runImportJob(job, tempFileAbsolutePath);
  }

  private void runImportJob(final Job job, final String tempFileAbsolutePath) {
    final long parameter = System.currentTimeMillis();

    final JobParameters jobParameters =
        new JobParametersBuilder()
            .addString("filePath", tempFileAbsolutePath)
            .addLong("startAt", parameter)
            .toJobParameters();

    try {
      jobLauncher.run(job, jobParameters);
    } catch (final JobExecutionAlreadyRunningException
        | JobRestartException
        | JobInstanceAlreadyCompleteException
        | JobParametersInvalidException exception) {
      final String message = exception.getMessage();

      log.error(message, exception);
    }
  }
}
