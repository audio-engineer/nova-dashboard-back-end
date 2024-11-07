package com.group6.nova.dashboard.backend.controller;

import com.group6.nova.dashboard.backend.model.CategorySalesDto;
import com.group6.nova.dashboard.backend.model.DailySalesDto;
import com.group6.nova.dashboard.backend.repository.OrderLineRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/// Controller for retrieval of data consumed by the dashboard charts.
///
/// @author Martin Kedmenec
@RestController
@RequestMapping("/api")
@ToString
public class ChartController {
  /// [DateTimeFormatter] instance
  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);

  /// [OrderLineRepository] instance
  private final OrderLineRepository orderLineRepository;

  /// [PagedResourcesAssembler] instance
  private final PagedResourcesAssembler<DailySalesDto> pagedResourcesAssembler;

  /// Constructor.
  ///
  /// @param orderLineRepositoryParameter OrderLineRepository instance
  /// @param pagedResourcesAssemblerParameter PagedResourcesAssembler instance
  public ChartController(
      final OrderLineRepository orderLineRepositoryParameter,
      final PagedResourcesAssembler<DailySalesDto> pagedResourcesAssemblerParameter) {
    orderLineRepository = orderLineRepositoryParameter;
    pagedResourcesAssembler = pagedResourcesAssemblerParameter;
  }

  /// Returns a list of dates and sales per category.
  ///
  /// @param startDate start date of the query
  /// @param endDate end date of the query
  /// @param pageable Pageable instance
  /// @return a HATEOAS list of dates and sales per category for each date
  @GetMapping("/daily-sales-by-category")
  public final PagedModel<EntityModel<DailySalesDto>> getDailySalesByCategory(
      @RequestParam("startDate") @DateTimeFormat(iso = ISO.DATE) final LocalDate startDate,
      @RequestParam("endDate") @DateTimeFormat(iso = ISO.DATE) final LocalDate endDate,
      final Pageable pageable) {
    final List<Object[]> rawData =
        orderLineRepository.findDailySalesByCategory(startDate, endDate, pageable);

    @SuppressWarnings("NestedMethodCall")
    final List<DailySalesDto> groupedByDate =
        rawData.stream()
            .collect(
                Collectors.groupingBy(
                    row -> (LocalDate) row[0],
                    Collectors.mapping(
                        row -> new CategorySalesDto((String) row[1], (Long) row[2]),
                        Collectors.toList())))
            .entrySet()
            .stream()
            .map(entry -> new DailySalesDto(entry.getKey(), entry.getValue()))
            .toList();

    final Page<DailySalesDto> salesByCategoryPage =
        PageableExecutionUtils.getPage(groupedByDate, pageable, groupedByDate::size);

    return pagedResourcesAssembler.toModel(salesByCategoryPage);
  }
}
