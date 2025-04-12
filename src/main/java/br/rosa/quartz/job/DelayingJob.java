package br.rosa.quartz.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class DelayingJob implements Job {

	public static final String NUM_EXECUTIONS = "NumExecutions";
	public static final String EXECUTION_DELAY = "ExecutionDelay";
	private static int executeCount;

	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		System.err.println("---" + ctx.getJobDetail().getKey() + " executing.[" + new Date() + "]");

		JobDataMap data = ctx.getJobDetail().getJobDataMap();

		executeCount++;

		data.put(NUM_EXECUTIONS, executeCount);

		long delay = data.containsKey(EXECUTION_DELAY) ? data.getLong(EXECUTION_DELAY) : 5000l;

		try {
			Thread.sleep(delay);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.err
				.println("  -" + ctx.getJobDetail().getKey() + " complete (" + executeCount + ") - time:" + new Date());
	}
}
