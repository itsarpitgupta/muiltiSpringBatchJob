package com.arpitjava.springbootbatch.step;

import org.springframework.batch.item.ItemProcessor;

import com.arpitjava.springbootbatch.enums.Job;

public class Processor implements ItemProcessor<String, String> {

	private String jobName;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	@Override
	public String process(String arg0) throws Exception {
		if(jobName.equals(Job.JOB1.toString())) {
			return arg0.toUpperCase();
		}else if(jobName.equals(Job.JOB2.toString())) {
			return arg0.toLowerCase();
		}
		return null;
	}

}
