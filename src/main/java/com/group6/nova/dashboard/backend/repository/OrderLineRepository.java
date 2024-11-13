package com.group6.nova.dashboard.backend.repository;

import com.group6.nova.dashboard.backend.model.DailyCategorySalesProjection;
import com.group6.nova.dashboard.backend.model.DailyHourlySalesProjection;
import com.group6.nova.dashboard.backend.model.OrderLine;
import com.group6.nova.dashboard.backend.model.TotalDailySalesProjection;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/// Repository interface for managing [OrderLine] entities.
///
/// @author Martin Kedmenec
public interface OrderLineRepository extends JpaRepository<OrderLine, UUID> {
  /// Returns the total number of sales for each category from and to a specific date.
  ///
  /// @param startDate start date
  /// @param endDate end date
  /// @return list of categories with total sales
  @Query(
      """
          select o.order.businessDate as date,
            o.product.category.name as categoryName,
            sum(o.quantity) as totalSales
          from OrderLine o
          where o.order.businessDate between :startDate and :endDate
          group by o.order.businessDate, o.product.category.name
          order by o.order.businessDate
          """)
  List<DailyCategorySalesProjection> findDailyCategorySales(LocalDate startDate, LocalDate endDate);

  /// Returns the total number of sales for each hour of each day in the date range.
  ///
  /// @param startDate start date
  /// @param endDate end date
  /// @return list of hourly sales grouped by date
  @Query(
      """
          select o.order.businessDate as date,
            extract(hour from o.order.created) as hour,
            sum(o.quantity) as totalSales
          from OrderLine o
          where o.order.businessDate between :startDate and :endDate
          group by o.order.businessDate, extract(hour from o.order.created)
          order by o.order.businessDate, extract(hour from o.order.created)
          """)
  List<DailyHourlySalesProjection> findDailyHourlySales(LocalDate startDate, LocalDate endDate);

  /// Returns the total number of sales per day between two business dates.
  ///
  /// @param startDate start date
  /// @param endDate end date
  /// @param pageable Pageable instance
  /// @return list of daily total sales
  @Query(
      """
          select o.order.businessDate as date,
            sum(o.quantity) as totalSales
          from OrderLine o
          where o.order.businessDate between :startDate and :endDate
          group by o.order.businessDate
          order by o.order.businessDate
          """)
  Page<TotalDailySalesProjection> findTotalDailySales(
      LocalDate startDate, LocalDate endDate, Pageable pageable);
}
