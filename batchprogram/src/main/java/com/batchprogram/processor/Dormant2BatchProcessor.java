package com.batchprogram.processor;

import java.time.LocalDate;

import org.springframework.batch.item.ItemProcessor;

import com.batchprogram.model.MemberDormant;
import com.batchprogram.model.Order;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Dormant2BatchProcessor implements ItemProcessor<Order, Order> {

	//static Long add = (long) 1002;
	
		@Override
	    public Order process(Order item) throws Exception {
	        log.debug(item.toString());	       
	        return item;
	        
		}
}
