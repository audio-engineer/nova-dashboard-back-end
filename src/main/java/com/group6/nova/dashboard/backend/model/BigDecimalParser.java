package com.group6.nova.dashboard.backend.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import lombok.extern.slf4j.Slf4j;

/// Service for parsing strings into [BigDecimal].
///
/// String prices such as `29,42` are not easy to natively convert to [BigDecimal].
/// [#parseStringIntoBigDecimal] converts such a number to `29.42` and calls a callback with a
/// method, e.g., a setter, that takes this number and applies it.
///
/// @author Martin Kedmenec
/// @see DecimalFormatSymbols
/// @see DecimalFormat
@Slf4j
enum BigDecimalParser {
  ;

  /// Grouping separator of the source data
  private static final char GROUPING_SEPARATOR = '.';

  /// Decimal separator of the source data
  private static final char DECIMAL_SEPARATOR = ',';

  /// [ThreadLocal] for [DecimalFormat] instance
  private static final ThreadLocal<DecimalFormat> DECIMAL_FORMAT_THREAD_LOCAL =
      ThreadLocal.withInitial(BigDecimalParser::supplier);

  private static DecimalFormat supplier() {
    final DecimalFormatSymbols symbols = new DecimalFormatSymbols();

    symbols.setGroupingSeparator(GROUPING_SEPARATOR);
    symbols.setDecimalSeparator(DECIMAL_SEPARATOR);

    final DecimalFormat decimalFormat = new DecimalFormat("#,##0.0#", symbols);
    decimalFormat.setParseBigDecimal(true);

    return decimalFormat;
  }

  /* default */ static BigDecimal parseStringIntoBigDecimal(final String value) {
    BigDecimal result = null;

    try {
      result = (BigDecimal) DECIMAL_FORMAT_THREAD_LOCAL.get().parse(value);
    } catch (final ParseException parseException) {
      final String parseExceptionMessage = parseException.getMessage();

      log.error("Error parsing value: {}", parseExceptionMessage);
    }

    return result;
  }
}
