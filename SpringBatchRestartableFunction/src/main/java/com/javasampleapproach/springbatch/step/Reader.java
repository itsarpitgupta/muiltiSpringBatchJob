package com.javasampleapproach.springbatch.step;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
 
 
public class Reader implements ItemReader<String>{
 
    private String[] files = {"/home/arpit-java/info"};
     
    public static int  count=0;
     
    Logger logger = LoggerFactory.getLogger(this.getClass());
     
    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
         
        if(count < files.length){
            return files[count++];
        }else{
            count=0;
        }
        return null;
    }
}	