package com.group6.nova.dashboard.backend.configuration;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/// General application configuration.
///
/// Used primarily for generic configuration options like [EnableAsync].
///
/// @author Martin Kedmenec
/// @see EnableAsync
@Configuration
@EnableAsync
@NoArgsConstructor
class ApplicationConfiguration {}
