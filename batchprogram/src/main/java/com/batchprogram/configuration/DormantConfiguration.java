package com.batchprogram.configuration;


import javax.sql.DataSource;

import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.batchprogram.Incrementer.CurrentTimeIncrementer;
import com.batchprogram.listener.JobCompletionNotificationListener;
import com.batchprogram.processor.Dormant2BatchProcessor;


import com.batchprogram.processor.Dormant1BatchProcessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableBatchProcessing
@ConditionalOnProperty(prefix = "spring.batch.job", name = "names", havingValue = "dormantJob")
public class DormantConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	
	@Bean("dormant1BatchProcess")
	@StepScope
	public Dormant1BatchProcessor dormant1BatchProcess() {
		return new Dormant1BatchProcessor();
	}

	@Bean("dormant2BatchProcess")
	@StepScope
	public Dormant2BatchProcessor dormant2BatchProcess() {
		return new Dormant2BatchProcessor();
	}
	
	
	
	@Bean("dormantJob")
	public Job dormantBatchJob(JobCompletionNotificationListener listener, Step stepChunk1,Step stepChunk2) {
		return jobBuilderFactory.get("dormantJob") 
				.incrementer(new CurrentTimeIncrementer())
				.listener(listener)
				.start(startLogStep())
				.next(stepChunk1)
				.next(stepChunk2)
				
				
				.build();
	}

	@Bean("startLogStep")
	public Step startLogStep() {
		return stepBuilderFactory.get("startLogStep") //simpleStep1이란 이름으로 스탭 생성 
				.tasklet((contribution, chunkContext) -> {
					//tasklet은 스탭 안에서단일로 수행될 커스텀한 기능을 선언
					log.info("*********************************");
					log.info(">>>> 휴면 계정 처리 프로그램 시작 <<<< ");
					log.info("*********************************");
					return RepeatStatus.FINISHED;
				})
				.build();
	}
	
	@Bean("stepChunk1")
	public Step stepChunk1(
			@Qualifier("myBatisPagingItemReader") MyBatisPagingItemReader myBatisPagingItemReader,
			@Qualifier("dormant1BatchProcess") ItemProcessor dormant1BatchProcess,
			@Qualifier("myBatisBatchItemWriter") MyBatisBatchItemWriter myBatisBatchItemWriter) {
		
					log.info("1. order_date가 3개월이 지난 경우 휴면 처리 ");
					
		return stepBuilderFactory
				.get("stepChunk1")
				.chunk(1)
				.reader(myBatisPagingItemReader)
				.processor(dormant1BatchProcess)
				.writer(myBatisBatchItemWriter)
				.build();
	}
	
	
	
	
	
	
	@Bean("stepChunk2")
	public Step stepChunk2(
			@Qualifier("myBatisPagingItemReader") MyBatisPagingItemReader myBatisPagingItemReader,
			@Qualifier("dormant2BatchProcess") ItemProcessor dormant2BatchProcess,
			@Qualifier("myBatisBatchItemWriter2") MyBatisBatchItemWriter myBatisBatchItemWriter2) {
		
				log.info("2. 휴면 처리한 order 체크 ");
				
		return stepBuilderFactory
				.get("stepChunk2")
				.chunk(1)
				.reader(myBatisPagingItemReader)
				.processor(dormant2BatchProcess)
				.writer(myBatisBatchItemWriter2)
				.build();
	}
	
	
}



