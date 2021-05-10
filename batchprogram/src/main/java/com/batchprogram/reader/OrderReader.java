package com.batchprogram.reader;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
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

import com.batchprogram.model.Order;


@Configuration
public class OrderReader {

	@Bean(name = "oDatasource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource") //application.properties의 spring.datasource 정보 fix
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "sqlSessionOrderdbFactory")
    public SqlSessionFactory sqlSessionProductFactory(@Qualifier("oDatasource") DataSource datasorce, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(datasorce);
        factoryBean.setConfigLocation(applicationContext.getResource("classpath:/mybatis/mybatis-config.xml"));
        factoryBean.setMapperLocations(applicationContext.getResources("classpath:/mybatis/order/*.xml"));
        return factoryBean.getObject();
    }

    @Primary
    @Bean(name = "sqlSessionOrderdb")
    public SqlSessionTemplate sqlSessionProduct(@Qualifier("sqlSessionOrderdbFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate;
    }

    @Primary
    @Bean(name = "oTX")
    public PlatformTransactionManager ProductTransactionManager(@Qualifier("oDatasource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name ="myBatisPagingItemReader")
    @StepScope
    public MyBatisPagingItemReader<Order> adbRead(@Qualifier("sqlSessionOrderdbFactory") SqlSessionFactory sqlSessionFactory) {
        MyBatisPagingItemReader<Order> myBatisPagingItemReader = new MyBatisPagingItemReader<Order>();
        myBatisPagingItemReader.setQueryId("selectOrder2");
        myBatisPagingItemReader.setSqlSessionFactory(sqlSessionFactory);
        myBatisPagingItemReader.setPageSize(10);
        return myBatisPagingItemReader;
    }
    
    @Bean(name ="myBatisPagingItemReaderC5")
    @StepScope
    public MyBatisPagingItemReader<Order> CCRead(@Qualifier("sqlSessionOrderdbFactory") SqlSessionFactory sqlSessionFactory) {
        MyBatisPagingItemReader<Order> myBatisPagingItemReader = new MyBatisPagingItemReader<Order>();
        myBatisPagingItemReader.setQueryId("selectOrder3");
        myBatisPagingItemReader.setSqlSessionFactory(sqlSessionFactory);
        myBatisPagingItemReader.setPageSize(10);
        return myBatisPagingItemReader;
    }
}
