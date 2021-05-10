package com.batchprogram.processor;

import org.springframework.batch.item.ItemProcessor;

import com.batchprogram.model.Coupon;
import com.batchprogram.model.Member;
import com.batchprogram.model.MemberDormant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Coupon1BatchProcessor implements ItemProcessor<Member, Coupon> {

	@Override
    public Coupon process(Member member) throws Exception {

		 log.debug(member.toString());
		 
		 final Long member_id = member.getMember_id();
		 log.debug(member_id.toString());
		 
		 
		 final Coupon coupon = new Coupon(member_id,1);
		 
		 log.debug(coupon.toString());
		 return coupon;
		 
	}
}