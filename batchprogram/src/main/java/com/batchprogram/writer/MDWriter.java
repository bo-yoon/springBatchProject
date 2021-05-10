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

import com.batchprogram.model.MemberDormant;
//import com.batchprogram.model.Test;

@Configuration
public class MDWriter {

	@Bean(name="mdDatasource")
    //@ConfigurationProperties(prefix = "spring.md.datasource") //application.properties의 spring.bdb.datasource 정보 fix
	@ConfigurationProperties(prefix = "spring.datasource") 
	public DataSource mysqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "sqlSessionMDdbFactory")
    public SqlSessionFactory sqlSessionProductFactory(@Qualifier("mdDatasource") DataSource datasorce, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(datasorce);
        factoryBean.setConfigLocation(applicationContext.getResource("classpath:/mybatis/mybatis-config.xml"));
        factoryBean.setMapperLocations(applicationContext.getResources("classpath:/mybatis/md/*.xml"));
        return factoryBean.getObject();
    }

    @Primary
    @Bean(name = "sqlSessionMDdb")
    public SqlSessionTemplate sqlSessionProduct(@Qualifier("sqlSessionMDdbFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate;
    }
    
    @Primary
    @Bean(name = "mTX")
    public PlatformTransactionManager ProductTransactionManager(@Qualifier("mdDatasource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name ="myBatisBatchItemWriter")
    @StepScope
    public MyBatisBatchItemWriter<MemberDormant> writer(@Qualifier("sqlSessionMDdbFactory")SqlSessionFactory sqlSessionFactory){
        MyBatisBatchItemWriter<MemberDormant> myBatisBatchItemWriter = new MyBatisBatchItemWriter<MemberDormant>();
        myBatisBatchItemWriter.setSqlSessionFactory(sqlSessionFactory);
        myBatisBatchItemWriter.setStatementId("insertMD");
        return myBatisBatchItemWriter;
    }
}
