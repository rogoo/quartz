package br.rosa.quartz.job;

import java.util.Date;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobInterruptableJob implements InterruptableJob {

	private static final Logger log = LoggerFactory.getLogger(JobInterruptableJob.class);
	private boolean interrupted;
	private String jobKey;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		jobKey = context.getJobDetail().getKey().getName();
		log.info("### " + jobKey + " starting at " + new Date());

		try {
			while (true) {
				Thread.sleep(1000);
				if (interrupted) {
					log.info("### Job " + jobKey + " was INTERRUPTED");
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		log.info("### The job " + jobKey + " was interrupted");
		interrupted = true;
	}

}
