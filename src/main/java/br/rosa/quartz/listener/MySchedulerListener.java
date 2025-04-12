package br.rosa.quartz.listener;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

public class MySchedulerListener implements SchedulerListener {

	@Override
	public void jobScheduled(Trigger trigger) {
		System.out.println("### SCHED LISTENER - jobScheduled: " + trigger.getKey());
	}

	@Override
	public void jobUnscheduled(TriggerKey triggerKey) {
		System.out.println("### SCHED LISTENER - jobUnscheduled: " + triggerKey);
	}

	@Override
	public void triggerFinalized(Trigger trigger) {
		System.out.println("### SCHED LISTENER - triggerFinalized: " + trigger.getKey());
	}

	@Override
	public void triggerPaused(TriggerKey triggerKey) {
		System.out.println("### SCHED LISTENER - triggerPaused: " + triggerKey);
	}

	@Override
	public void triggersPaused(String triggerGroup) {
		System.out.println("### SCHED LISTENER - triggersPaused: " + triggerGroup);
	}

	@Override
	public void triggerResumed(TriggerKey triggerKey) {
		System.out.println("### SCHED LISTENER - triggerResumed: " + triggerKey);
	}

	@Override
	public void triggersResumed(String triggerGroup) {
		System.out.println("### SCHED LISTENER - triggersResumed: " + triggerGroup);
	}

	@Override
	public void jobAdded(JobDetail jobDetail) {
		System.out.println("### SCHED LISTENER - jobAdded: " + jobDetail);
	}

	@Override
	public void jobDeleted(JobKey jobKey) {
		System.out.println("### SCHED LISTENER - jobDeleted: " + jobKey);
	}

	@Override
	public void jobPaused(JobKey jobKey) {
		System.out.println("### SCHED LISTENER - jobPaused: " + jobKey);
	}

	@Override
	public void jobsPaused(String jobGroup) {
		System.out.println("### SCHED LISTENER - jobsPaused: " + jobGroup);
	}

	@Override
	public void jobResumed(JobKey jobKey) {
		System.out.println("### SCHED LISTENER - jobResumed: " + jobKey);
	}

	@Override
	public void jobsResumed(String jobGroup) {
		System.out.println("### SCHED LISTENER - jobsResumed: " + jobGroup);
	}

	@Override
	public void schedulerError(String msg, SchedulerException cause) {
		System.out.println("### SCHED LISTENER - schedulerError. msg is: " + msg + " and cause is: " + cause);
	}

	@Override
	public void schedulerInStandbyMode() {
		System.out.println("### SCHED LISTENER - schedulerInStandbyMode");
	}

	@Override
	public void schedulerStarted() {
		System.out.println("### SCHED LISTENER - schedulerStarted");
	}

	@Override
	public void schedulerStarting() {
		System.out.println("### SCHED LISTENER - schedulerStarting");
	}

	@Override
	public void schedulerShutdown() {
		System.out.println("### SCHED LISTENER - schedulerShutdown");
	}

	@Override
	public void schedulerShuttingdown() {
		System.out.println("### SCHED LISTENER - schedulerShuttingdown");
	}

	@Override
	public void schedulingDataCleared() {
		System.out.println("### SCHED LISTENER - schedulingDataCleared");
	}

}
