package com.yoga.assignment.orderbook.api.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yoga.assignment.orderbook.api.enums.OrderSide;
import com.yoga.assignment.orderbook.api.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ORDERS")
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Getter
@Setter
@ToString
public class Order {	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private BigDecimal price;	

	private Long quantity;
	
	@Enumerated(EnumType.STRING)
	private OrderSide side;	
	
	@Column(columnDefinition = "TIMESTAMP")
	private Date lastUpdated;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	private Long matchedQuantity;
	
	private Long remainingQuantity;

}
