package com.javasampleapproach.springbatch.controller;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
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
	
    /*@Autowired
    Job job;*/
 
    @RequestMapping("/launchjob")
    public String handle() throws Exception {
 
        Logger logger = LoggerFactory.getLogger(this.getClass());
        JobExecution jobExecution = null;
        try {
        	Job job = jobLocator.getJob("job");
        	jobExecution = jobLauncher.run(job, new JobParameters());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
 
        return "jobExecution's info: Id = " + jobExecution.getId() + " ,status = " + jobExecution.getStatus();
    }
}