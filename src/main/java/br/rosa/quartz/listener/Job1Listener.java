package br.rosa.quartz.listener;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.rosa.quartz.job.SimpleJob2;

public class Job1Listener implements JobListener {

	private static Logger log = LoggerFactory.getLogger(Job1Listener.class);

	@Override
	public String getName() {
		return "job1_to_job2";
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		log.info("Job1Listener says: Job Is about to be executed.");
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		log.info("Job1Listener says: Job Execution was vetoed.");
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		log.info("Job1Listener says: Job was executed.");

		JobDetail job = JobBuilder.newJob(SimpleJob2.class).withIdentity("job2").build();
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("job2Trigger").startNow().build();

		try {
			context.getScheduler().scheduleJob(job, trigger);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
