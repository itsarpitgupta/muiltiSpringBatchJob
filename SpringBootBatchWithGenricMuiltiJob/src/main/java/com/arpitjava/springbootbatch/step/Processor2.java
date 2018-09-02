package com.arpitjava.springbootbatch.step;

import org.springframework.batch.item.ItemProcessor;

public class Processor2 implements ItemProcessor<String, String> {

	@Override
	public String process(String arg0) throws Exception {
		/*if(true) {
			throw new RuntimeException();
		}*/
		return arg0.toLowerCase();
//		return null;
	}

}
