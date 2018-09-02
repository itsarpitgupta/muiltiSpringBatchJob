package com.arpitjava.springbootbatch.step;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

public class Writer implements ItemWriter<String> {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void write(List<? extends String> messages) throws Exception {
		for (String string : messages) {
			System.out.println(string);
		}
		
	}

}
