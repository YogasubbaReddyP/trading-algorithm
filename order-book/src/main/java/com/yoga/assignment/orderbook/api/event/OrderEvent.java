package com.yoga.assignment.orderbook.api.event;

import org.springframework.context.ApplicationEvent;

import com.yoga.assignment.orderbook.api.model.Order;

public class OrderEvent extends ApplicationEvent {
	
	private static final long serialVersionUID = -8018639892773401383L;
	
	private Order order;

	public OrderEvent(Object source, Order order) {
		super(source);
		this.order = order;

	}

	public Order getOrder() {
		return order;
	}	
	
}
