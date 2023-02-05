package com.yoga.assignment.orderbook.api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.yoga.assignment.orderbook.api.dto.OrderRequestDto;
import com.yoga.assignment.orderbook.api.exception.OrderException;
import com.yoga.assignment.orderbook.api.model.Order;

public interface OrderService {

	public ResponseEntity<Order> addOrder(OrderRequestDto order);

	public ResponseEntity<HttpStatus> deleteOrder(Long id);

	public ResponseEntity<Order> modifyOrder(Long id, OrderRequestDto modifiedOrder) throws OrderException;

	public ResponseEntity<Order> getOrderById(Long id) throws OrderException;

	public ResponseEntity<List<Order>> getAllOrders() throws OrderException;
}
