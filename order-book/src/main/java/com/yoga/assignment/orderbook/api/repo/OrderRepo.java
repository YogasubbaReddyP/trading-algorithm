package com.yoga.assignment.orderbook.api.repo;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yoga.assignment.orderbook.api.enums.OrderSide;
import com.yoga.assignment.orderbook.api.enums.OrderStatus;
import com.yoga.assignment.orderbook.api.model.Order;


public interface OrderRepo extends JpaRepository<Order, Long> {

	@Query("FROM Order o where o.side = ?1 and price =?2 and status != ?3 order by lastUpdated")
	public List<Order> findBySideAndPriceAndNotStatus(OrderSide side, BigDecimal price, OrderStatus status);
	
}
