package com.arpitjava.springbootbatch.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.configuration.DuplicateJobException;
import org.springframework.batch.core.configuration.JobFactory;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.configuration.support.ReferenceJobFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.arpitjava.springbootbatch.listener.SkipExecutionListener;
import com.arpitjava.springbootbatch.pojo.Job;
import com.arpitjava.springbootbatch.pojo.Processor;
import com.arpitjava.springbootbatch.pojo.Reader;
import com.arpitjava.springbootbatch.pojo.Step;
import com.arpitjava.springbootbatch.pojo.Writer;

@Configuration
public class CustomConfig {

	@Autowired
	JobRegistry jobRegistry;

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
		JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
		jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
		return jobRegistryBeanPostProcessor;
	}

	@Bean
	public List<Job> customJobs() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		List<Job> jobs = new ArrayList<Job>();

		Job job1 = new Job();
		job1.setId(1);
		job1.setJobName(com.arpitjava.springbootbatch.enums.Job.JOB1.toString());

		Step step1 = new Step();
		step1.setId(1);
		step1.setName(com.arpitjava.springbootbatch.enums.Step.STEP1.toString());
		step1.setChunkSize(1);

		Reader reader = new Reader();
		reader.setId(1);
		reader.setName(com.arpitjava.springbootbatch.enums.Reader.READER1.toString());
		reader.setReaderClassName("com.arpitjava.springbootbatch.step.Reader");

		Writer writer = new Writer();
		writer.setId(1);
		writer.setName(com.arpitjava.springbootbatch.enums.Writer.WRITER1.toString());
		writer.setWriterClassName("com.arpitjava.springbootbatch.step.Writer");

		Processor processor = new Processor();
		processor.setId(1);
		processor.setName(com.arpitjava.springbootbatch.enums.Processor.PROCESSOR1.toString());
		processor.setProcessorClassName("com.arpitjava.springbootbatch.step.Processor");

		step1.setReader(reader);
		step1.setWriter(writer);
		step1.setProcessor(processor);

		job1.getSteps().add(step1);

		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		Job job2 = new Job();
		job2.setId(2);
		job2.setJobName(com.arpitjava.springbootbatch.enums.Job.JOB2.toString());

		Step step2 = new Step();
		step2.setId(2);
		step2.setName(com.arpitjava.springbootbatch.enums.Step.STEP2.toString());
		step2.setChunkSize(1);

		Reader reader1 = new Reader();
		reader1.setId(2);
		reader1.setName(com.arpitjava.springbootbatch.enums.Reader.READER2.toString());
		reader1.setReaderClassName("com.arpitjava.springbootbatch.step.Reader2");

		Writer writer1 = new Writer();
		writer1.setId(2);
		writer1.setName(com.arpitjava.springbootbatch.enums.Writer.WRITER2.toString());
		writer1.setWriterClassName("com.arpitjava.springbootbatch.step.Writer2");

		Processor processor1 = new Processor();
		processor1.setId(2);
		processor1.setName(com.arpitjava.springbootbatch.enums.Processor.PROCESSOR2.toString());
		processor1.setProcessorClassName("com.arpitjava.springbootbatch.step.Processor2");

		step2.setReader(reader1);
		step2.setWriter(writer1);
		step2.setProcessor(processor1);

		job2.getSteps().add(step2);

		jobs.add(job1);
		jobs.add(job2);

		return jobs;
	}

	@Bean
	public List<org.springframework.batch.core.Job> registerJobs()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, DuplicateJobException {
		List<Job> jobs = customJobs();
		List<org.springframework.batch.core.Job> batchJobList = new ArrayList<org.springframework.batch.core.Job>();
		System.out.println("Creating a new job and register...");
		for (com.arpitjava.springbootbatch.pojo.Job job : jobs) {
			List<com.arpitjava.springbootbatch.pojo.Step> steps = job.getSteps();
			List<org.springframework.batch.core.Step> batchSteps = new ArrayList<org.springframework.batch.core.Step>();

			for (com.arpitjava.springbootbatch.pojo.Step step : steps) {

				Class reader = Class.forName(step.getReader().getReaderClassName());
				ItemReader<String> itemReader = (ItemReader) reader.newInstance();

				Class processor = Class.forName(step.getProcessor().getProcessorClassName());
				ItemProcessor<String, String> itemProcessor = (ItemProcessor) processor.newInstance();

				Class writer = Class.forName(step.getWriter().getWriterClassName());
				ItemWriter<String> itemWriter = (ItemWriter) writer.newInstance();

				org.springframework.batch.core.Step batchStep = stepBuilderFactory.get(step.getName())
						.<String, String>chunk(step.getChunkSize()).reader(itemReader).processor(itemProcessor)
						.writer(itemWriter).listener(skipListener()).build();
				batchSteps.add(batchStep);
			}

			org.springframework.batch.core.Job batchJob = jobBuilderFactory.get(job.getJobName())
					.incrementer(new RunIdIncrementer()).flow(batchSteps.get(0)).end().build();
			// addJobInContext(batchJob,job.getJobName());
			batchJobList.add(batchJob);
			jobRegistry.register(new ReferenceJobFactory(batchJob));
		}
		return batchJobList;
	}

	public SkipExecutionListener skipListener() {
		return new SkipExecutionListener();
	}

}