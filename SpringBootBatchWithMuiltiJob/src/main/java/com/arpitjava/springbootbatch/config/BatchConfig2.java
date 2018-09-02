package com.arpitjava.springbootbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.arpitjava.springbootbatch.listener.SkipExecutionListener;
import com.arpitjava.springbootbatch.step.Processor2;
import com.arpitjava.springbootbatch.step.Reader2;
import com.arpitjava.springbootbatch.step.Writer2;

@Configuration
public class BatchConfig2 {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean(name="JOB2")
	public Job job() {
		return jobBuilderFactory.get(com.arpitjava.springbootbatch.constant.Job.JOB2.toString()).incrementer(new RunIdIncrementer()).flow(step1()).end().build();

	}

	@Bean(name="STEP1")
	public Step step1() {
		return stepBuilderFactory.get(com.arpitjava.springbootbatch.constant.Step.STEP1.toString()).<String, String>chunk(10).reader(new Reader2()).processor(new Processor2())
				.writer(new Writer2()).listener(skipListener()).build();
	}

	public SkipExecutionListener skipListener() {
		return new SkipExecutionListener();
	}
}
