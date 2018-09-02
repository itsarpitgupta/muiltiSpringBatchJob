package com.arpitjava.springbootbatch.pojo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
@Data
public class Job {

	private long id;
	private String jobName;
	private List<Step> steps=new ArrayList<Step>();
}
