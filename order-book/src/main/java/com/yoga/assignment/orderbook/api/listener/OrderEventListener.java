package com.yoga.assignment.orderbook.api.listener;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.yoga.assignment.orderbook.api.enums.OrderSide;
import com.yoga.assignment.orderbook.api.event.OrderEvent;
import com.yoga.assignment.orderbook.api.service.MatchingEngine;

@Component
public class OrderEventListener implements ApplicationListener<OrderEvent> {

	@Autowired
	private MatchingEngine matchingEngine;

	@Override
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
	@Transactional(value = TxType.REQUIRES_NEW)
	public void onApplicationEvent(OrderEvent event) {
		if (OrderSide.BUY == event.getOrder().getSide()) {
			matchingEngine.matchBuyOrder(event.getOrder());
		} else {
			matchingEngine.matchSellOrder(event.getOrder());
		}
	}
}
