package com.batchprogram.processor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemProcessor;

import com.batchprogram.model.Coupon;
import com.batchprogram.model.Member;
import com.batchprogram.model.Order;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Coupon3BatchProcessor implements ItemProcessor<Order, Order> {

	
	@Override
    public Order process(Order order) throws Exception {

		 log.debug(order.toString());
		 
		 return order;
		 
	}
	
}
