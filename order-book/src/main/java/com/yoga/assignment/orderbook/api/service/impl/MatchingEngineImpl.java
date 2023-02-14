package com.yoga.assignment.orderbook.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.assignment.orderbook.api.enums.OrderSide;
import com.yoga.assignment.orderbook.api.enums.OrderStatus;
import com.yoga.assignment.orderbook.api.model.Order;
import com.yoga.assignment.orderbook.api.repo.OrderRepo;
import com.yoga.assignment.orderbook.api.service.MatchingEngine;

@Service
public class MatchingEngineImpl implements MatchingEngine {

	@Autowired
	private OrderRepo orderRepo;

	@Override
	public void matchBuyOrder(Order order) {

		Optional<Order> optional = orderRepo.findById(order.getId());
		if (optional.isPresent()) {
			List<Order> orders = orderRepo.findBySideAndPriceAndNotStatus(OrderSide.SELL, order.getPrice(),
					OrderStatus.MATCHED);
			Order buyOrder = optional.get();
			if (orders.isEmpty()) {
				buyOrder.setStatus(OrderStatus.REGISTERED);
				buyOrder.setRemainingQuantity(buyOrder.getQuantity());
				buyOrder.setMatchedQuantity(0l);
				buyOrder.setLastUpdated(buyOrder.getLastUpdated());
				orderRepo.save(buyOrder);
			} else {

				long qunatityToMatched = buyOrder.getRemainingQuantity() != null ? buyOrder.getRemainingQuantity()
						: buyOrder.getQuantity();

				for (Order sellOrder : orders) {
					if (sellOrder.getRemainingQuantity() == qunatityToMatched) {

						sellOrder.setRemainingQuantity(sellOrder.getRemainingQuantity() - qunatityToMatched);
						sellOrder.setMatchedQuantity(sellOrder.getMatchedQuantity() + qunatityToMatched);
						sellOrder.setStatus(OrderStatus.MATCHED);

						orderRepo.save(sellOrder);

						buyOrder.setRemainingQuantity(buyOrder.getQuantity());
						buyOrder.setMatchedQuantity(0l);

						buyOrder.setStatus(OrderStatus.MATCHED);
						buyOrder.setRemainingQuantity(buyOrder.getRemainingQuantity() - qunatityToMatched);
						buyOrder.setMatchedQuantity(buyOrder.getMatchedQuantity() + qunatityToMatched);
						qunatityToMatched = qunatityToMatched - sellOrder.getRemainingQuantity();

						orderRepo.save(buyOrder);
						return;

					} else if (sellOrder.getRemainingQuantity() < qunatityToMatched) {

						Long sellOrderQuantityToMatch = sellOrder.getRemainingQuantity();
						sellOrder.setRemainingQuantity(0l);
						sellOrder.setMatchedQuantity(sellOrder.getMatchedQuantity() + sellOrderQuantityToMatch);
						sellOrder.setStatus(OrderStatus.MATCHED);

						orderRepo.save(sellOrder);

						buyOrder.setRemainingQuantity(buyOrder.getRemainingQuantity() == null ? buyOrder.getQuantity() : buyOrder.getRemainingQuantity());
						buyOrder.setMatchedQuantity(buyOrder.getMatchedQuantity() != null ? buyOrder.getMatchedQuantity() :0l);

						buyOrder.setStatus(OrderStatus.PARTIALLY_MATCHED);
						buyOrder.setRemainingQuantity(buyOrder.getRemainingQuantity() - sellOrderQuantityToMatch);
						buyOrder.setMatchedQuantity(buyOrder.getMatchedQuantity() + sellOrderQuantityToMatch);
						qunatityToMatched = qunatityToMatched - sellOrderQuantityToMatch;

						orderRepo.save(buyOrder);

					} else if (sellOrder.getRemainingQuantity() > qunatityToMatched) {

						sellOrder.setRemainingQuantity(sellOrder.getRemainingQuantity() - qunatityToMatched);
						sellOrder.setMatchedQuantity(sellOrder.getMatchedQuantity() + qunatityToMatched);
						sellOrder.setStatus(OrderStatus.PARTIALLY_MATCHED);
						orderRepo.save(sellOrder);

						buyOrder.setRemainingQuantity(buyOrder.getRemainingQuantity() == null ? buyOrder.getQuantity() : buyOrder.getRemainingQuantity());
						buyOrder.setMatchedQuantity(buyOrder.getMatchedQuantity() != null ? buyOrder.getMatchedQuantity() :0l);

						buyOrder.setStatus(OrderStatus.MATCHED);
						buyOrder.setRemainingQuantity(0l);
						buyOrder.setMatchedQuantity(qunatityToMatched);
						orderRepo.save(buyOrder);
						qunatityToMatched = 0l;
					}
				}
			}
		}
	}

	@Override
	public void matchSellOrder(Order order) {

		Optional<Order> optional = orderRepo.findById(order.getId());
		if (optional.isPresent()) {
			List<Order> orders = orderRepo.findBySideAndPriceAndNotStatus(OrderSide.BUY, order.getPrice(),
					OrderStatus.MATCHED);
			Order sellOrder = optional.get();
			if (orders.isEmpty()) {
				sellOrder.setStatus(OrderStatus.REGISTERED);
				sellOrder.setRemainingQuantity(sellOrder.getQuantity());
				sellOrder.setMatchedQuantity(0l);
				sellOrder.setLastUpdated(sellOrder.getLastUpdated());
				orderRepo.save(sellOrder);
			} else {

				long qunatityToMatched = sellOrder.getRemainingQuantity() != null ? sellOrder.getRemainingQuantity()
						: sellOrder.getQuantity();

				for (Order buyOrder : orders) {
					if (qunatityToMatched != 0l && buyOrder.getRemainingQuantity() == qunatityToMatched) {

						buyOrder.setRemainingQuantity(buyOrder.getRemainingQuantity() - qunatityToMatched);
						buyOrder.setMatchedQuantity(buyOrder.getMatchedQuantity() + qunatityToMatched);
						buyOrder.setStatus(OrderStatus.MATCHED);

						orderRepo.save(buyOrder);

						sellOrder.setRemainingQuantity(sellOrder.getRemainingQuantity() == null ? sellOrder.getQuantity() : sellOrder.getRemainingQuantity());
						sellOrder.setMatchedQuantity(sellOrder.getMatchedQuantity() != null ? sellOrder.getMatchedQuantity() :0l);

						sellOrder.setStatus(OrderStatus.MATCHED);
						sellOrder.setRemainingQuantity(sellOrder.getRemainingQuantity() - qunatityToMatched);
						sellOrder.setMatchedQuantity(sellOrder.getMatchedQuantity() + qunatityToMatched);
						qunatityToMatched = qunatityToMatched - buyOrder.getRemainingQuantity();

						orderRepo.save(sellOrder);
						return;

					} else if (qunatityToMatched != 0l && buyOrder.getRemainingQuantity() < qunatityToMatched) {

						Long buyOrderQuantityToMatch = buyOrder.getRemainingQuantity();
						buyOrder.setRemainingQuantity(0l);
						buyOrder.setMatchedQuantity(buyOrder.getMatchedQuantity() + buyOrderQuantityToMatch);
						buyOrder.setStatus(OrderStatus.MATCHED);

						orderRepo.save(buyOrder);

						sellOrder.setRemainingQuantity(sellOrder.getRemainingQuantity() == null ? sellOrder.getQuantity() : sellOrder.getRemainingQuantity());
						sellOrder.setMatchedQuantity(sellOrder.getMatchedQuantity() != null ? sellOrder.getMatchedQuantity() :0l);

						sellOrder.setStatus(OrderStatus.PARTIALLY_MATCHED);
						sellOrder.setRemainingQuantity(sellOrder.getRemainingQuantity() - buyOrderQuantityToMatch);
						sellOrder.setMatchedQuantity(sellOrder.getMatchedQuantity() + buyOrderQuantityToMatch);
						qunatityToMatched = qunatityToMatched - buyOrderQuantityToMatch;

						orderRepo.save(sellOrder);

					} else if (qunatityToMatched != 0l && buyOrder.getRemainingQuantity() > qunatityToMatched) {

						buyOrder.setRemainingQuantity(buyOrder.getRemainingQuantity() - qunatityToMatched);
						buyOrder.setMatchedQuantity(buyOrder.getMatchedQuantity() + qunatityToMatched);
						buyOrder.setStatus(OrderStatus.PARTIALLY_MATCHED);
						orderRepo.save(buyOrder);

						sellOrder.setRemainingQuantity(sellOrder.getRemainingQuantity() == null ? sellOrder.getQuantity() : sellOrder.getRemainingQuantity());
						sellOrder.setMatchedQuantity(sellOrder.getMatchedQuantity() != null ? sellOrder.getMatchedQuantity() :0l);

						sellOrder.setStatus(OrderStatus.MATCHED);
						sellOrder.setRemainingQuantity(0l);
						sellOrder.setMatchedQuantity(qunatityToMatched);
						orderRepo.save(sellOrder);
						qunatityToMatched = 0l;
					}
				}
			}
		}
	}

}
