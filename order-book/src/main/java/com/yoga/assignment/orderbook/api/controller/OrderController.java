package com.yoga.assignment.orderbook.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yoga.assignment.orderbook.api.dto.OrderRequestDto;
import com.yoga.assignment.orderbook.api.exception.OrderException;
import com.yoga.assignment.orderbook.api.model.Order;
import com.yoga.assignment.orderbook.api.service.OrderService;

@RestController
public class OrderController {
	
	@Autowired
	private OrderService orderService;

	@PostMapping("/addOrder")
	public ResponseEntity<Order> addOrder(@RequestBody @Valid OrderRequestDto orderRequestDto) {
		return orderService.addOrder(orderRequestDto);
	}

	@DeleteMapping("deleteOrderById/{id}")
	public ResponseEntity<HttpStatus> deleteOrder(@PathVariable Long id) {
		return orderService.deleteOrder(id);
	}

	@PostMapping("modifyOrderById/{id}")
	public ResponseEntity<Order> modifyOrder(@PathVariable Long id, @RequestBody OrderRequestDto modifiedOrder) throws OrderException {
		return orderService.modifyOrder(id, modifiedOrder);
	}

	@GetMapping("/getOrderById/{id}")
	public ResponseEntity<Order> getOrderById(@PathVariable Long id) throws OrderException {
		return orderService.getOrderById(id);
	}

	@GetMapping("/getAllOrder")
	public ResponseEntity<List<Order>> getAllOrders() throws OrderException{
	return orderService.getAllOrders();
	}
}
		


