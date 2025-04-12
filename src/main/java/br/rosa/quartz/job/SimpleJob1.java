package br.rosa.quartz.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleJob1 implements Job {

	private static final Logger log = LoggerFactory.getLogger(SimpleJob1.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("### SimpleJob1 says: " + context.getJobDetail().getKey() + " executing at " + new Date());
	}
}
