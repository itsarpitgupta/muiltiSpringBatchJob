package com.arpitjava.springbootbatch.pojo;

import lombok.Data;

@Data
public class Step {
	private long id;
	private String name;
	private int chunkSize;
	private Reader reader;
	private Writer writer;
	private Processor processor;

}
