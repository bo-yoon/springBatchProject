package com.batchprogram.model;


import lombok.Getter;

import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
//import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Date;



@Getter @Setter
@ToString
public class Order {

    
    private Long order_id;

    private LocalDate order_date; // 주문시간
    
    //private String status; // ORDER, CANCEL
    
    //private Long delivery_id;
    
    private Long member_id;

    
    
}
