package com.group6.nova.dashboard.backend.repository;

import com.group6.nova.dashboard.backend.model.OrderLine;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
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
  /// @param pageable Pageable instance
  /// @return list of categories with total sales
  @Query(
      """
      select o.order.businessDate as date, o.product.category.name as categoryName,
            sum(o.quantity) as totalSales
            from OrderLine o
            where o.order.businessDate between :startDate and :endDate
            group by o.order.businessDate, o.product.category.name
            order by o.order.businessDate
      """)
  List<Object[]> findDailySalesByCategory(
      LocalDate startDate, LocalDate endDate, Pageable pageable);
}
