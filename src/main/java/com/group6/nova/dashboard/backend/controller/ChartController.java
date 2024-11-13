package com.group6.nova.dashboard.backend.controller;

import com.group6.nova.dashboard.backend.model.CategorySalesDto;
import com.group6.nova.dashboard.backend.model.DailyCategorySalesDto;
import com.group6.nova.dashboard.backend.model.DailyCategorySalesProjection;
import com.group6.nova.dashboard.backend.model.DailyHourlySalesDto;
import com.group6.nova.dashboard.backend.model.DailyHourlySalesProjection;
import com.group6.nova.dashboard.backend.model.HourlySalesDto;
import com.group6.nova.dashboard.backend.model.TotalDailySalesProjection;
import com.group6.nova.dashboard.backend.repository.OrderLineRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
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
  /// [OrderLineRepository] instance
  private final OrderLineRepository orderLineRepository;

  /// [PagedResourcesAssembler] instance
  private final PagedResourcesAssembler<DailyCategorySalesDto> pagedResourcesAssemblerDailyCategory;

  /// [PagedResourcesAssembler] instance
  private final PagedResourcesAssembler<DailyHourlySalesDto> pagedResourcesAssemblerDailyHourly;

  /// [PagedResourcesAssembler] instance
  private final PagedResourcesAssembler<TotalDailySalesProjection>
      pagedResourcesAssemblerTotalDaily;

  /// Constructor.
  ///
  /// @param orderLineRepositoryParameter OrderLineRepository instance
  /// @param pagedResourcesAssemblerDailyCategoryParameter PagedResourcesAssembler instance
  /// @param pagedResourcesAssemblerDailyHourlyParameter PagedResourcesAssembler instance
  /// @param pagedResourcesAssemblerTotalDailyParameter PagedResourcesAssembler instance
  public ChartController(
      final OrderLineRepository orderLineRepositoryParameter,
      final PagedResourcesAssembler<DailyCategorySalesDto>
          pagedResourcesAssemblerDailyCategoryParameter,
      final PagedResourcesAssembler<DailyHourlySalesDto>
          pagedResourcesAssemblerDailyHourlyParameter,
      final PagedResourcesAssembler<TotalDailySalesProjection>
          pagedResourcesAssemblerTotalDailyParameter) {
    orderLineRepository = orderLineRepositoryParameter;
    // [Objects#requireNonNull(Object)] is a workaround for SpotBugs error EI_EXPOSE_REP2
    pagedResourcesAssemblerDailyCategory =
        Objects.requireNonNull(
            pagedResourcesAssemblerDailyCategoryParameter,
            "pagedResourcesAssemblerDailyCategoryParameter must not be null");
    pagedResourcesAssemblerDailyHourly =
        Objects.requireNonNull(
            pagedResourcesAssemblerDailyHourlyParameter,
            "pagedResourcesAssemblerDailyHourlyParameter must not be null");
    pagedResourcesAssemblerTotalDaily =
        Objects.requireNonNull(
            pagedResourcesAssemblerTotalDailyParameter,
            "pagedResourcesAssemblerTotalDailyParameter must not be null");
  }

  /// Returns a list of dates and sales per category.
  ///
  /// @param startDate start date of the query
  /// @param endDate end date of the query
  /// @param pageable Pageable instance
  /// @return a HATEOAS list of dates and sales per category for each date
  @GetMapping("/daily-category-sales")
  public final PagedModel<EntityModel<DailyCategorySalesDto>> getDailyCategorySales(
      @RequestParam("startDate") @DateTimeFormat(iso = ISO.DATE) final LocalDate startDate,
      @RequestParam("endDate") @DateTimeFormat(iso = ISO.DATE) final LocalDate endDate,
      final Pageable pageable) {
    final List<DailyCategorySalesProjection> dailyCategorySalesProjections =
        orderLineRepository.findDailyCategorySales(startDate, endDate);

    @SuppressWarnings("NestedMethodCall")
    final List<DailyCategorySalesDto> dailyCategorySalesDtos =
        dailyCategorySalesProjections.stream()
            .collect(Collectors.groupingBy(DailyCategorySalesProjection::getDate))
            .entrySet()
            .stream()
            .map(
                entry ->
                    new DailyCategorySalesDto(
                        entry.getKey(),
                        entry.getValue().stream()
                            .map(e -> new CategorySalesDto(e.getCategoryName(), e.getTotalSales()))
                            .toList()))
            .toList();

    final Page<DailyCategorySalesDto> dailyCategorySalesDtoPage =
        PageableExecutionUtils.getPage(
            dailyCategorySalesDtos, pageable, dailyCategorySalesDtos::size);

    return pagedResourcesAssemblerDailyCategory.toModel(dailyCategorySalesDtoPage);
  }

  /// Returns a list of dates and sales per category.
  ///
  /// @param startDate start date of the query
  /// @param endDate end date of the query
  /// @param pageable Pageable instance
  /// @return a HATEOAS list of dates and sales per category for each date
  @GetMapping("/daily-hourly-sales")
  public final PagedModel<EntityModel<DailyHourlySalesDto>> getDailyHourlySales(
      @RequestParam("startDate") @DateTimeFormat(iso = ISO.DATE) final LocalDate startDate,
      @RequestParam("endDate") @DateTimeFormat(iso = ISO.DATE) final LocalDate endDate,
      final Pageable pageable) {
    final List<DailyHourlySalesProjection> dailyHourlySalesProjections =
        orderLineRepository.findDailyHourlySales(startDate, endDate);

    @SuppressWarnings("NestedMethodCall")
    final List<DailyHourlySalesDto> dailyHourlySalesDtos =
        dailyHourlySalesProjections.stream()
            .collect(Collectors.groupingBy(DailyHourlySalesProjection::getDate))
            .entrySet()
            .stream()
            .map(
                entry ->
                    new DailyHourlySalesDto(
                        entry.getKey(),
                        entry.getValue().stream()
                            .map(
                                e ->
                                    new HourlySalesDto(
                                        LocalTime.of(e.getHour(), 0), e.getTotalSales()))
                            .toList()))
            .toList();

    final Page<DailyHourlySalesDto> dailyHourlySalesDtoPage =
        PageableExecutionUtils.getPage(dailyHourlySalesDtos, pageable, dailyHourlySalesDtos::size);

    return pagedResourcesAssemblerDailyHourly.toModel(dailyHourlySalesDtoPage);
  }

  /// Returns a list of dates and sales per category.
  ///
  /// @param startDate start date of the query
  /// @param endDate end date of the query
  /// @param pageable Pageable instance
  /// @return a HATEOAS list of dates and sales per category for each date
  @GetMapping("/total-daily-sales")
  public final PagedModel<EntityModel<TotalDailySalesProjection>> getTotalDailySales(
      @RequestParam("startDate") @DateTimeFormat(iso = ISO.DATE) final LocalDate startDate,
      @RequestParam("endDate") @DateTimeFormat(iso = ISO.DATE) final LocalDate endDate,
      final Pageable pageable) {
    final Page<TotalDailySalesProjection> totalTotalDailySalesProjections =
        orderLineRepository.findTotalDailySales(startDate, endDate, pageable);

    return pagedResourcesAssemblerTotalDaily.toModel(totalTotalDailySalesProjections);
  }
}
