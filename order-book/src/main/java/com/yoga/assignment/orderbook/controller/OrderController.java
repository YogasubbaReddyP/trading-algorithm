package com.yoga.assignment.orderbook.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yoga.assignment.orderbook.model.Order;
import com.yoga.assignment.orderbook.repo.OrderRepo;

@RestController
public class OrderController {
	@Autowired
	private OrderRepo orderRepo;

	@PostMapping("/addOrder")
	public ResponseEntity<Order> addOrder(@RequestBody Order order) {
		try {
			Order orderObject = orderRepo.save(order);
			return new ResponseEntity<>(orderObject, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("deleteOrderById/{id}")
	public ResponseEntity<HttpStatus> deleteOrder(@PathVariable Long id) {
		try {
			orderRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("modifyOrderById/{id}")
	public ResponseEntity<Order> modifyOrder(@PathVariable Long id, @RequestBody Order modifiedOrder) {
		try {
			Optional<Order> existingOrder = orderRepo.findById(id);
			if (existingOrder.isPresent()) {
				Order latestOrder = existingOrder.get();
				if (latestOrder.getPrice() != modifiedOrder.getPrice()) {
					latestOrder.setPrice(modifiedOrder.getPrice());
				}
				if (latestOrder.getQuantity() != modifiedOrder.getQuantity()) {
					latestOrder.setQuantity(modifiedOrder.getQuantity());
				}
				if (latestOrder.getSide() != modifiedOrder.getSide()) {
					latestOrder.setSide(modifiedOrder.getSide());
				}
				Order latestOrderObj = orderRepo.save(latestOrder);
				return new ResponseEntity<>(latestOrderObj, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getOrderById/{id}")
	public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
		try {
			Optional<Order> order = orderRepo.findById(id);
			if (order.isPresent()) {
				return new ResponseEntity<>(order.get(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getAllOrder")
	public ResponseEntity<List<Order>> getAllOrders() {
		try {
			List<Order> orders = orderRepo.findAll();

			if (orders.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(orders, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
