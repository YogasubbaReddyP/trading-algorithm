package com.yoga.assignment.orderbook.api.service;

import com.yoga.assignment.orderbook.api.model.Order;

public interface MatchingEngine {

	public void matchBuyOrder(Order order);

	public void matchSellOrder(Order order);	

}
