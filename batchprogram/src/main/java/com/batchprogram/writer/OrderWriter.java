package com.batchprogram.writer;


import org.apache.ibatis.session.SqlSessionFactory;

import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.batchprogram.model.Order;

@Configuration
public class OrderWriter {


    @Bean(name ="myBatisBatchItemWriter2")
    @StepScope
    public MyBatisBatchItemWriter<Order> writer(@Qualifier("sqlSessionOrderdbFactory")SqlSessionFactory sqlSessionFactory){
        MyBatisBatchItemWriter<Order> myBatisBatchItemWriter = new MyBatisBatchItemWriter<Order>();
        myBatisBatchItemWriter.setSqlSessionFactory(sqlSessionFactory);
        myBatisBatchItemWriter.setStatementId("updateOrder");
        return myBatisBatchItemWriter;
    }
    
    
    @Bean(name ="myBatisBatchItemWriterCP")
    @StepScope
    public MyBatisBatchItemWriter<Order> writer2(@Qualifier("sqlSessionOrderdbFactory")SqlSessionFactory sqlSessionFactory){
        MyBatisBatchItemWriter<Order> myBatisBatchItemWriter = new MyBatisBatchItemWriter<Order>();
        myBatisBatchItemWriter.setSqlSessionFactory(sqlSessionFactory);
        myBatisBatchItemWriter.setStatementId("updateOrder2");
        return myBatisBatchItemWriter;
    }
}
