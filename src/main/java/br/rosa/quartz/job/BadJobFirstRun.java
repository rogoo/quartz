package br.rosa.quartz.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BadJobFirstRun implements Job {

	private static final Logger log = LoggerFactory.getLogger(BadJobExceptionBetterRemove.class);
	private static int denominator = 0;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("###" + context.getJobDetail().getKey() + " executing at " + new Date() + " with denominator: "
				+ denominator);
		int calculation = 0;
		denominator++;
		try {
			calculation = 1234 / denominator;
		} catch (Exception e) {
			JobExecutionException e2 = new JobExecutionException();

			e2.setRefireImmediately(true);
			throw e2;
		}
		System.out.println(calculation);
	}
}
