package br.rosa.quartz.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloJob implements Job {

	private static Logger log = LoggerFactory.getLogger(HelloJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("\n### The job is" + context.getJobDetail().getKey() + " - Yooo World! - " + new Date() + "\n");
	}
}
