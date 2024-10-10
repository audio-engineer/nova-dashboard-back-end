package com.group6.novadashboardbackend;

/// Contains the IDs of various checks and lints by different tools.
/// Handy for suppressing warnings.
public enum WarningValue {
  ;
  /// The ID of the JetBrains DesignForExtension inspection
  public static final String DESIGN_FOR_EXTENSION = "DesignForExtension";

  /// The ID of the PMD SignatureDeclareThrowsException check
  public static final String PMD_SIGNATURE_DECLARE_THROWS_EXCEPTION =
      "PMD.SignatureDeclareThrowsException";

  /// The ID of the JetBrains ProhibitedExceptionDeclared inspection
  public static final String PROHIBITED_EXCEPTION_DECLARED = "ProhibitedExceptionDeclared";
}
