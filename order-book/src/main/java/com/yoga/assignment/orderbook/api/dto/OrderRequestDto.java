package com.yoga.assignment.orderbook.api.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.yoga.assignment.orderbook.api.enums.OrderSide;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class OrderRequestDto {

	@NotNull(message = "Price can not be empty or null")
	private BigDecimal price;

	@NotNull(message = "quantity can not be empty or null")
	private Long quantity;
	
	@NotNull(message = "OrderSide can not be empty or null")
	private OrderSide side;

}
