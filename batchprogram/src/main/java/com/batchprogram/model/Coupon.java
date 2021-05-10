package com.batchprogram.model;

import static javax.persistence.FetchType.LAZY;

import java.time.LocalDateTime;


import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public  class Coupon {

	    
	    private Long coupon_id; // 쿠폰 코드 
	    
	    private LocalDateTime created_date; // 생성시간
	    
	    private LocalDateTime end_date; // 유효 날짜 
	    
	 
	    private CouponStatus status; // UNUSED, USED
	    
	 
	    private Long member_id;
	    
	    public String kind;
	    
	    public String dtype;
	    
	    public Coupon() {
	    	
	    }
	    
	    public Coupon(Long member_id, int kind) {
	    	
        	this.created_date = LocalDateTime.now();
        	LocalDateTime now = LocalDateTime.now();
        	
        	//실제 모드는 : 1달 
        	LocalDateTime endDate2 = now.plusMonths(1);
        	this.end_date = endDate2;
        	this.member_id = member_id;
        	this.status = CouponStatus.UNUSED;
        	if(kind == 1) {
        		this.kind = "1주년쿠폰";
        		this.dtype = "ANNI";
        	}else if (kind == 5){
        		this.kind = "소비쿠폰";
        		this.dtype = "Con";
        	}
        	
	    }
	    
}
