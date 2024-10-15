package com.group6.nova.dashboard.backend;

/// Contains the IDs of various checks and lints by different tools.
///
/// This enum provides static values representing the IDs of inspections or lints, which are
/// useful for suppressing specific warnings in the codebase.
/// To be used in conjunction with [SuppressWarnings].
///
/// Inspection IDs:
/// - [WarningValue#DESIGN_FOR_EXTENSION] - the ID for the JetBrains `DesignForExtension`
/// inspection.
///
/// @author Martin Kedmenec
public enum WarningValue {
  ;
  /// The ID of the JetBrains DesignForExtension inspection
  public static final String DESIGN_FOR_EXTENSION = "DesignForExtension";
}
