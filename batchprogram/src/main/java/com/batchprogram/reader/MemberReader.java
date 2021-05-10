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

import com.batchprogram.model.Member;

;

@Configuration
public class MemberReader {

	
	@Bean(name="memberDatasource")
//	@Qualifier("memberDatasource")
	 @ConfigurationProperties(prefix = "spring.datasource") 
	public DataSource mysqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "sqlSessionMemberdbFactory")
    public SqlSessionFactory sqlSessionProductFactory(@Qualifier("memberDatasource") DataSource datasorce, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(datasorce);
        factoryBean.setConfigLocation(applicationContext.getResource("classpath:/mybatis/mybatis-config.xml"));
        factoryBean.setMapperLocations(applicationContext.getResources("classpath:/mybatis/member/*.xml"));
        return factoryBean.getObject();
    }

    @Primary
    @Bean(name = "sqlSessionMemberdb")
    public SqlSessionTemplate sqlSessionProduct(@Qualifier("sqlSessionMemberdbFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate;
    }
    
    @Primary
    @Bean(name = "mbTX")
    public PlatformTransactionManager ProductTransactionManager(@Qualifier("memberDatasource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name ="myBatisPagingItemReader2")
    @StepScope
    public MyBatisPagingItemReader<Member> adbRead(@Qualifier("sqlSessionMemberdbFactory") SqlSessionFactory sqlSessionFactory) {
        MyBatisPagingItemReader<Member> myBatisPagingItemReader = new MyBatisPagingItemReader<Member>();
        myBatisPagingItemReader.setQueryId("selectMember2");
        myBatisPagingItemReader.setSqlSessionFactory(sqlSessionFactory);
        myBatisPagingItemReader.setPageSize(10);
        return myBatisPagingItemReader;
    }
    
    //where minute(join_date) > minute(now());
    
    @Bean(name ="myBatisPagingItemReaderC")
    @StepScope
    public MyBatisPagingItemReader<Member> CRead(@Qualifier("sqlSessionMemberdbFactory") SqlSessionFactory sqlSessionFactory) {
        MyBatisPagingItemReader<Member> myBatisPagingItemReader = new MyBatisPagingItemReader<Member>();
        myBatisPagingItemReader.setQueryId("selectMember4");
        myBatisPagingItemReader.setSqlSessionFactory(sqlSessionFactory);
        myBatisPagingItemReader.setPageSize(10);
        return myBatisPagingItemReader;
    }
}
