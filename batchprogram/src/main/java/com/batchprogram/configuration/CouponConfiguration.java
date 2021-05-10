package com.batchprogram.configuration;


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
import com.batchprogram.processor.Coupon1BatchProcessor;
import com.batchprogram.processor.Coupon2BatchProcessor;
import com.batchprogram.processor.Coupon3BatchProcessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableBatchProcessing
@ConditionalOnProperty(prefix = "spring.batch.job", name = "names", havingValue = "CouponJob")
public class CouponConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	
	
	@Bean("Coupon1BatchProcess")
	@StepScope
	public Coupon1BatchProcessor Coupon1BatchProcess() {
		return new Coupon1BatchProcessor();
	}

	@Bean("Coupon2BatchProcess")
	@StepScope
	public Coupon2BatchProcessor Coupon2BatchProcess() {
		return new Coupon2BatchProcessor();
	}
	
	
	@Bean("Coupon3BatchProcess")
	@StepScope
	public Coupon3BatchProcessor Coupon3BatchProcess() {
		return new Coupon3BatchProcessor();
	}
	
	
	@Bean("CouponJob")
	public Job CouponBatchJob(JobCompletionNotificationListener listener, Step stepCoupon1, Step stepCoupon2, Step stepCoupon3) {
		return jobBuilderFactory.get("CouponJob") //("testChunkJob")
				.incrementer(new CurrentTimeIncrementer())
				.listener(listener)
				.start(startLog2Step())
				.next(stepCoupon1)
				.next(stepCoupon2)
				.next(stepCoupon3)
				.build();
	}
	
	
	@Bean("startLog2Step")
	public Step startLog2Step() {
		return stepBuilderFactory.get("startLog2Step") //simpleStep1이란 이름으로 스탭 생성 
				.tasklet((contribution, chunkContext) -> {
					//tasklet은 스탭 안에서단일로 수행될 커스텀한 기능을 선언
					log.info("*********************************");
					log.info(">>>> 쿠폰 처리 배치  프로그램 시작 <<<< ");
					log.info("*********************************");
					return RepeatStatus.FINISHED;
				})
				.build();
	}
	
	
	@Bean("stepCoupon1")
	public Step stepCoupon1(
			@Qualifier("myBatisPagingItemReaderC") MyBatisPagingItemReader myBatisPagingItemReader,
			@Qualifier("Coupon1BatchProcess") ItemProcessor Coupon1BatchProcess,
			@Qualifier("myBatisBatchItemWriter4") MyBatisBatchItemWriter myBatisBatchItemWriter) {
		
					log.info("1. 가입한지 1년 지나면 쿠폰 발급  ");
					
		return stepBuilderFactory
				.get("stepCoupon1")
				.chunk(1)
				.reader(myBatisPagingItemReader)
				.processor(Coupon1BatchProcess)
				.writer(myBatisBatchItemWriter)
				.build();
	}
	
	@Bean("stepCoupon2")
	public Step stepCoupon2(
			@Qualifier("myBatisPagingItemReaderC5") MyBatisPagingItemReader myBatisPagingItemReader,
			@Qualifier("Coupon2BatchProcess") ItemProcessor Coupon2BatchProcess,
			@Qualifier("myBatisBatchItemWriter4") MyBatisBatchItemWriter myBatisBatchItemWriter) {
		
					log.info("2. 주문이 5개 이상이면 쿠폰 발행   ");
					// 주문이 5개인지 확인을 하
					
		return stepBuilderFactory
				.get("stepCoupon2")
				.chunk(1)
				.reader(myBatisPagingItemReader)
				.processor(Coupon2BatchProcess)
				.writer(myBatisBatchItemWriter)
				.build();
	}
	
	@Bean("stepCoupon3")
	public Step stepCoupon3(
			@Qualifier("myBatisPagingItemReaderC5") MyBatisPagingItemReader myBatisPagingItemReader,
			@Qualifier("Coupon3BatchProcess") ItemProcessor Coupon3BatchProcess,
			@Qualifier("myBatisBatchItemWriterCP") MyBatisBatchItemWriter myBatisBatchItemWriter) {
		
					log.info("3. 쿠폰 발행한 주문, 체크 ");
					// 주문이 5개인지 확인을 하
					
		return stepBuilderFactory
				.get("stepCoupon3")
				.chunk(1)
				.reader(myBatisPagingItemReader)
				.processor(Coupon3BatchProcess)
				.writer(myBatisBatchItemWriter)
				.build();
	}
	
}
