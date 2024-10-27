package com.group6.nova.dashboard.backend.batchprocessing;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.function.Consumer;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/// Service for parsing strings into [BigDecimal].
///
/// String prices such as `29,42` are not easy to natively convert to [BigDecimal].
/// [#parseStringIntoBigDecimal] converts such a number to `29.42` and calls a callback with a
/// method, e.g., a setter, that takes this number and applies it.
///
/// @author Martin Kedmenec
/// @see DecimalFormatSymbols
/// @see DecimalFormat
@Service
@Slf4j
@ToString
class BigDecimalParserService {
  /// Grouping separator of the source data
  private static final char GROUPING_SEPARATOR = '.';

  /// Decimal separator of the source data
  private static final char DECIMAL_SEPARATOR = ',';

  /// [DecimalFormat] instance
  private final DecimalFormat decimalFormat;

  /// Constructor.
  /* default */ BigDecimalParserService() {
    final DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();

    decimalFormatSymbols.setGroupingSeparator(GROUPING_SEPARATOR);
    decimalFormatSymbols.setDecimalSeparator(DECIMAL_SEPARATOR);

    decimalFormat = new DecimalFormat("#,##0.0#", decimalFormatSymbols);
    decimalFormat.setParseBigDecimal(true);
  }

  /* default */ final void parseStringIntoBigDecimal(
      final String value, final Consumer<? super BigDecimal> setter) {
    try {
      final BigDecimal bigDecimalValue = (BigDecimal) decimalFormat.parse(value);

      setter.accept(bigDecimalValue);
    } catch (final ParseException parseException) {
      final String parseExceptionMessage = parseException.getMessage();

      log.error("Error parsing value: {}", parseExceptionMessage);
    }
  }
}
