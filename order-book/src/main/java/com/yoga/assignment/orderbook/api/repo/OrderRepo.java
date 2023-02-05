package com.yoga.assignment.orderbook.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yoga.assignment.orderbook.api.model.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {

}
