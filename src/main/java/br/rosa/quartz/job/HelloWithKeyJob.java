package br.rosa.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWithKeyJob implements Job {

	private static Logger log = LoggerFactory.getLogger(HelloWithKeyJob.class);

	public HelloWithKeyJob() {
		super();
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("Yooo World! - The key job name is: '" + context.getJobDetail().getKey() + "/"
				+ context.getTrigger().getKey() + "'");
	}
}
