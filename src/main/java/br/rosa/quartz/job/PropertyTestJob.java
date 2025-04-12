package br.rosa.quartz.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class PropertyTestJob implements Job {

	public PropertyTestJob() {
		super();
	}

	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		JobDataMap data = ctx.getMergedJobDataMap();

		System.out.println("Instance " + ctx.getJobDetail().getKey() + " of PropertyJob says: "
				+ data.getString("jobSays") + ", and val is: " + data.getFloatValue("myFloatValue"));
	}
}
