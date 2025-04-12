package br.rosa.quartz.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TriggerJob implements Job {

	private static Logger log = LoggerFactory.getLogger(TriggerJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("\n\n### The trigger is" + context.getTrigger().getKey() + " - TRIGGER World! - executed at: "
				+ new Date() + "\n");
	}
}
