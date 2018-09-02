package com.arpitjava.springbootbatch.step;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class Reader2 implements ItemReader<String> {

	final private String[] mesg = { "Arpit", "Kuomar", "Gupta" };
	private int count = 0;

 

	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (count < mesg.length) {
			return mesg[count++];
		} else {
			count = 0;
		}

		return null;
	}

}
