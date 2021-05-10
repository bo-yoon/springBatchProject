package com.batchprogram.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.batchprogram.model.Coupon;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class CouponDao {

	@Resource(name = "sqlSessionCoupondb")
    private SqlSessionTemplate sqlSessionCoupondb;

    public List<Coupon> selectCoupon() {
        return this.sqlSessionCoupondb.selectList("selectCoupon");
    }
    
    
}
