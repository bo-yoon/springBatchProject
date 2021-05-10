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

import com.batchprogram.model.Coupon;
import com.batchprogram.model.Member;

@Configuration
public class CouponReader {

//	@Qualifier("cDatasource")
	@Bean(name="cDatasource")
    //@ConfigurationProperties(prefix = "spring.bdb.datasource") //application.properties의 spring.bdb.datasource 정보 fix
	@ConfigurationProperties(prefix = "spring.datasource") 
	public DataSource mysqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "sqlSessionCoupondbFactory")
    public SqlSessionFactory sqlSessionProductFactory(@Qualifier("cDatasource") DataSource datasorce, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(datasorce);
        factoryBean.setConfigLocation(applicationContext.getResource("classpath:/mybatis/mybatis-config.xml"));
        factoryBean.setMapperLocations(applicationContext.getResources("classpath:/mybatis/coupon/*.xml"));
        return factoryBean.getObject();
    }

    @Primary
    @Bean(name = "sqlSessionCoupondb")
    public SqlSessionTemplate sqlSessionProduct(@Qualifier("sqlSessionCoupondbFactory") 
    SqlSessionFactory sqlSessionFactory) throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate;
    }
    
    @Primary
    @Bean(name = "cTX")
    public PlatformTransactionManager ProductTransactionManager(@Qualifier("cDatasource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    
    @Bean(name ="myBatisPagingItemReaderCP")
    @StepScope
    public MyBatisPagingItemReader<Coupon> Read(@Qualifier("sqlSessionCoupondbFactory") SqlSessionFactory sqlSessionFactory) {
        MyBatisPagingItemReader<Coupon> myBatisPagingItemReader = new MyBatisPagingItemReader<Coupon>();
        myBatisPagingItemReader.setQueryId("selectCoupon3");
        myBatisPagingItemReader.setSqlSessionFactory(sqlSessionFactory);
        myBatisPagingItemReader.setPageSize(10);
        return myBatisPagingItemReader;
    }
    

}
