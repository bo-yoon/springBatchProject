package com.batchprogram.processor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemProcessor;

import com.batchprogram.model.Coupon;
import com.batchprogram.model.Member;
import com.batchprogram.model.Order;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Coupon2BatchProcessor implements ItemProcessor<Order, Coupon> {

	
	@Override
    public Coupon process(Order order) throws Exception {

		 log.debug(order.toString());
		 
		 final Long member_id = order.getMember_id();
		 log.debug(member_id.toString());
		
		 final Coupon coupon = new Coupon(member_id,5);
		 
		 log.debug(coupon.toString());
		 
		 return coupon;
		 
	}
	
}
