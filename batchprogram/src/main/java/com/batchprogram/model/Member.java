package com.batchprogram.model;


import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import javax.persistence.Entity;

@Entity
@Getter @Setter
public class Member {

    private Long member_id;
    private String name;
    private LocalDateTime joinDate; // 주문시간
    private String city;
    private String street;
    private String zipcode;
    private DormantStatus dormantStatus;
    
    public Member() {
    	
    }
    
    
    
    
    
}
