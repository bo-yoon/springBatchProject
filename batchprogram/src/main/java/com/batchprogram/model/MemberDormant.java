package com.batchprogram.model;

import lombok.Builder;
//import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.time.LocalDateTime;




@ToString
@Getter @Setter
public class MemberDormant {

	
    private Long dormant_id;

	
    private Long member_id;
	
	
	private LocalDateTime dormant_date; // 휴면 계정 전환 시

	
	private String status; // active, sleep
   
	//@Builder
	public MemberDormant() {}

	//@Builder
	public MemberDormant(Long m_id) {
		
		this.member_id = m_id;
		this.dormant_date = LocalDateTime.now();
		this.status = "ACTIVE";
		
	}
    
}







