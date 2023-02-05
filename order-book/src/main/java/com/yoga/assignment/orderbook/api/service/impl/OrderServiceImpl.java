package com.yoga.assignment.orderbook.api.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yoga.assignment.orderbook.api.dto.OrderRequestDto;
import com.yoga.assignment.orderbook.api.exception.OrderException;
import com.yoga.assignment.orderbook.api.model.Order;
import com.yoga.assignment.orderbook.api.repo.OrderRepo;
import com.yoga.assignment.orderbook.api.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepo orderRepo;

	@Override
	public ResponseEntity<Order> addOrder(OrderRequestDto orderRequestDto) {
		try {
			Order order = Order.build((long) 0, orderRequestDto.getPrice(), orderRequestDto.getQuantity(),
					orderRequestDto.getSide(), new Date());
			return new ResponseEntity<>(orderRepo.save(order), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<HttpStatus> deleteOrder(Long id) {
		try {
			orderRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Order> modifyOrder(Long id, OrderRequestDto modifiedOrder) throws OrderException {

		Optional<Order> existingOrder = orderRepo.findById(id);

		if (existingOrder.isPresent()) {
			Order latestOrder = existingOrder.get();
			if (latestOrder.getPrice() != null && latestOrder.getPrice() != modifiedOrder.getPrice()) {
				latestOrder.setPrice(modifiedOrder.getPrice());
			}
			if (latestOrder.getQuantity() != null && latestOrder.getQuantity() != modifiedOrder.getQuantity()) {
				latestOrder.setQuantity(modifiedOrder.getQuantity());
			}
			if (latestOrder.getSide() != null && latestOrder.getSide() != modifiedOrder.getSide()) {
				latestOrder.setSide(modifiedOrder.getSide());
			}

			return new ResponseEntity<>(orderRepo.save(latestOrder), HttpStatus.OK);
		} else {
			throw new OrderException("No orders found with Id to modify: " + id);
		}
	}

	@Override
	public ResponseEntity<Order> getOrderById(Long id) throws OrderException {

		Optional<Order> order = orderRepo.findById(id);
		if (order.isPresent()) {
			return new ResponseEntity<>(order.get(), HttpStatus.OK);
		} else {
			throw new OrderException("No orders found with Id : " + id);
		}

	}

	@Override
	public ResponseEntity<List<Order>> getAllOrders() throws OrderException {

		List<Order> orders = orderRepo.findAll();

		if (orders.isEmpty()) {
			throw new OrderException("No orders found");
		} else {
			return new ResponseEntity<>(orders, HttpStatus.OK);
		}
	}

}
