package com.arpitjava.springbootbatch.controller;

import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobLauncherController {
 
	@Autowired
	JobLauncher jobLauncher;
 
	
	@Autowired
	JobLocator jobLocator;
 
	
 
	@RequestMapping("/launchjob1")
	public String launchjob1() throws Exception {
 
		Logger logger = LoggerFactory.getLogger(this.getClass());
		try {
			JobParameters jobParameters = new JobParametersBuilder().addString("JOB_NAME", com.arpitjava.springbootbatch.enums.Job.JOB1.toString()).addLong("time", System.currentTimeMillis()).toJobParameters();
			Job job = jobLocator.getJob(com.arpitjava.springbootbatch.enums.Job.JOB1.toString());
			jobLauncher.run(job, jobParameters);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
 
		return "Done";
	}
	
	@RequestMapping("/launchjob2")
	public String launchjob2() throws Exception {
 
		Logger logger = LoggerFactory.getLogger(this.getClass());
		try {
			JobParameters jobParameters = new JobParametersBuilder().addString("JOB_NAME", com.arpitjava.springbootbatch.enums.Job.JOB2.toString()).addLong("time", System.currentTimeMillis()).toJobParameters();
			Job job = jobLocator.getJob(com.arpitjava.springbootbatch.enums.Job.JOB2.toString());
			jobLauncher.run(job, jobParameters);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
 
		return "Done";
	}
	
}