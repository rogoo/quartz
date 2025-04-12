package br.rosa.quartz.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColorJob implements Job {

	private static Logger log = LoggerFactory.getLogger(ColorJob.class);

	public static final String FAVORITE_COLOR = "favorite color";
	public static final String EXECUTION_COUNT = "execution count";
	public static int counterStatic;
	private int counterPrivate;

	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		JobKey jobKey = ctx.getJobDetail().getKey();

		JobDataMap data = ctx.getJobDetail().getJobDataMap();
		String favoriteColor = data.getString(FAVORITE_COLOR);
		int count = data.getInt(EXECUTION_COUNT);
		log.info("ColorJob: " + jobKey + " executing at " + new Date() + "\n favorite color is " + favoriteColor
				+ "\n execution count (from job map) is " + "" + count
				+ "\n execution count (from job member variable) private is " + counterPrivate + " and static is "
				+ counterStatic);
		count++;
		System.out.println("### count: " + count);
		data.put(EXECUTION_COUNT, count);
		++counterPrivate;
		++counterStatic;
	}
}
