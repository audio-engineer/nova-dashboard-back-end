package com.group6.nova.dashboard.backend.service;

import org.springframework.batch.core.Job;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

/// Interface for launching batch import jobs asynchronously, using a provided job and multipart
/// file.
///
/// @author Martin Kedmenec
@FunctionalInterface
public interface ImportJob {
  /// Saves the multipart file to a temporal file in the filesystem, and, on success, proceeds to
  /// launch the batch job.
  ///
  /// @param job a Job instance
  /// @param multipartFile a MultipartFile instance
  @Async
  void launchImportJob(Job job, MultipartFile multipartFile);
}
