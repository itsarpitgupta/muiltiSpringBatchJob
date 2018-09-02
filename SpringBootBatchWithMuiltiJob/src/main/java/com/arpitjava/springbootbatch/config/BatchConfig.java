package com.arpitjava.springbootbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import com.arpitjava.springbootbatch.listener.SkipExecutionListener;
import com.arpitjava.springbootbatch.step.Processor;
import com.arpitjava.springbootbatch.step.Reader;
import com.arpitjava.springbootbatch.step.Writer;

@Configuration
public class BatchConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean(name="JOB1")
	public Job job() {
		return jobBuilderFactory.get(com.arpitjava.springbootbatch.constant.Job.JOB1.toString()).incrementer(new RunIdIncrementer()).flow(step1()).end().build();

	}

	@Bean(name="STEP2")
	public Step step1() {
		return stepBuilderFactory.get(com.arpitjava.springbootbatch.constant.Step.STEP2.toString()).<String, String>chunk(10).reader(new Reader()).processor(new Processor())
				.writer(new Writer()).listener(skipListener()).build();
	}

	public SkipExecutionListener skipListener() {
		return new SkipExecutionListener();
	}
}
