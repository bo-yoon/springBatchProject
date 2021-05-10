package com.batchprogram.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.batchprogram.model.Order;



@Repository
public class OrderDao {

    @Resource(name = "sqlSessionOrderdb")
    private SqlSessionTemplate sqlSessionOrderdb;

    public List<Order> selectOrder() {
        return this.sqlSessionOrderdb.selectList("selectOrder");
    }
    
    public int updateOrder2(Order order){
    	
    	return this.sqlSessionOrderdb.update("updateOrder2",order);
    }
    
   
}






