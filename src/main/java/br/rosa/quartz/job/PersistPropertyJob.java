package br.rosa.quartz.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PersistJobDataAfterExecution
public class PersistPropertyJob implements Job {

	public static final String COUNT = "COUNT";
	public static final Logger log = LoggerFactory.getLogger(PersistPropertyJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap data = context.getJobDetail().getJobDataMap();

		log.info("\n\n### job name is: " + context.getJobDetail().getKey() + " - the value of count is: "
				+ data.getInt(COUNT) + "\n");

		data.put(COUNT, data.getInt(COUNT) + 1);

		try {
			// Thread.sleep(3500);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
