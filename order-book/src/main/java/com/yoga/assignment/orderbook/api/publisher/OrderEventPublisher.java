package com.yoga.assignment.orderbook.api.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.yoga.assignment.orderbook.api.event.OrderEvent;
import com.yoga.assignment.orderbook.api.model.Order;

@Component
public class OrderEventPublisher {
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	public void publishOrderEvent(final Order order) {
		OrderEvent orderEvent = new OrderEvent(this, order);
		applicationEventPublisher.publishEvent(orderEvent);
	}
}
