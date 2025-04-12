package br.rosa.quartz.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BadJobExceptionBetterRemove implements Job {

	private static final Logger log = LoggerFactory.getLogger(BadJobFirstRun.class);
	private static int denominator = 0;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("###" + context.getJobDetail().getKey() + " executing at " + new Date() + " with denominator: "
				+ denominator);

		try {
			@SuppressWarnings("unused")
			int calculation = 1234 / 0;
		} catch (Exception e) {
			JobExecutionException e2 = new JobExecutionException();

			denominator++;

			e2.setUnscheduleAllTriggers(true);
			throw e2;
		}

		log.info("###" + context.getJobDetail().getKey() + " completed at " + new Date());
	}
}