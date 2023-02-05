package com.yoga.assignment.orderbook.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yoga.assignment.orderbook.model.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {

}
