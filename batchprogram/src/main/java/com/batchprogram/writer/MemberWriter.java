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

import com.batchprogram.model.Member;


@Configuration
public class MemberWriter {

	

    @Bean(name ="myBatisBatchItemWriter3")
    @StepScope
    public MyBatisBatchItemWriter<Member> writer(@Qualifier("sqlSessionMemberdbFactory")SqlSessionFactory sqlSessionFactory){
        MyBatisBatchItemWriter<Member> myBatisBatchItemWriter = new MyBatisBatchItemWriter<Member>();
        myBatisBatchItemWriter.setSqlSessionFactory(sqlSessionFactory);
        myBatisBatchItemWriter.setStatementId("updateMember");
        return myBatisBatchItemWriter;
    }
}
