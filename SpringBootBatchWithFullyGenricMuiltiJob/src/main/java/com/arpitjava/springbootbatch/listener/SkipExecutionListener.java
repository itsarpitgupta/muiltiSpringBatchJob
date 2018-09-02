package com.arpitjava.springbootbatch.listener;

import org.springframework.batch.core.SkipListener;

public class SkipExecutionListener implements SkipListener<String, String> {

	@Override
	public void onSkipInRead(Throwable t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSkipInWrite(String item, Throwable t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSkipInProcess(String item, Throwable t) {
		System.out.println("Skip on processor "+item);
	}

}
