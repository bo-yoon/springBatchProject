package com.batchprogram.configuration;


import javax.annotation.PostConstruct;
import javax.sql.DataSource;


import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import com.batchprogram.listener.ChunkExecutionListener;
import com.batchprogram.listener.JobCompletionNotificationListener;
import com.batchprogram.listener.StepExecutionNotificationListener;


import lombok.extern.slf4j.Slf4j;


@Slf4j
@Configuration
public class BatchConfiguration extends DefaultBatchConfigurer {


	@Value("${spring.batch.job.names:NONE}")
    private String jobNames;
	
	
    @Override
    public void setDataSource(DataSource dataSource) {
    }

//    @Autowired
//    @Override
//    public void setDataSource(@Qualifier("batchDataSource") DataSource batchDataSource) {
//        super.setDataSource(batchDataSource);
//    }
    
    
    @Bean
    public StepExecutionNotificationListener stepExecutionListener() {
        return new StepExecutionNotificationListener();
    }

    @Bean
    public ChunkExecutionListener chunkListener() {
        return new ChunkExecutionListener();
    }

    @Bean
    public JobCompletionNotificationListener jobExecutionListener() {
        return new JobCompletionNotificationListener();
    }
    
    @PostConstruct
    public void validateJobNames() {
        log.info("jobNames : {}", jobNames);
        if (jobNames.isEmpty() || jobNames.equals("NONE")) {
            throw new IllegalStateException("spring.batch.job.names=job1,job2 명시 !");
        }
    }
    

}
