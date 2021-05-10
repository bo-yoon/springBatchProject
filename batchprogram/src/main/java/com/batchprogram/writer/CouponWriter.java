package com.batchprogram.writer;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.batchprogram.model.Coupon;


@Configuration
public class CouponWriter {

	

    @Bean(name ="myBatisBatchItemWriter4")
    @StepScope
    public MyBatisBatchItemWriter<Coupon> writer(@Qualifier("sqlSessionCoupondbFactory")SqlSessionFactory sqlSessionFactory){
        MyBatisBatchItemWriter<Coupon> myBatisBatchItemWriter = new MyBatisBatchItemWriter<Coupon>();
        myBatisBatchItemWriter.setSqlSessionFactory(sqlSessionFactory);
        myBatisBatchItemWriter.setStatementId("insertCoupon");
        
        
        return myBatisBatchItemWriter;
    }
}
