package com.batchprogram.processor;



import org.springframework.batch.item.ItemProcessor;
import com.batchprogram.model.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Dormant1BatchProcessor implements ItemProcessor<Order, MemberDormant> {

	@Override
    public MemberDormant process(Order item) throws Exception {
        log.debug(item.toString());

        final Long member_id = item.getMember_id();   
        final MemberDormant md = new MemberDormant(member_id);
       
        return md;
        
	}
	
	
}


