package br.rosa.quartz;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.DateBuilder;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.AnnualCalendar;
import org.quartz.impl.matchers.KeyMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.rosa.quartz.job.BadJobExceptionBetterRemove;
import br.rosa.quartz.job.BadJobFirstRun;
import br.rosa.quartz.job.ColorJob;
import br.rosa.quartz.job.DelayingJob;
import br.rosa.quartz.job.HelloJob;
import br.rosa.quartz.job.HelloWithKeyJob;
import br.rosa.quartz.job.JobInterruptableJob;
import br.rosa.quartz.job.PersistPropertyJob;
import br.rosa.quartz.job.PropertyTestJob;
import br.rosa.quartz.job.SimpleJob1;
import br.rosa.quartz.job.TriggerJob;
import br.rosa.quartz.listener.Job1Listener;
import br.rosa.quartz.listener.MySchedulerListener;

public class App {

	private static Logger log = LoggerFactory.getLogger(App.class);

	public static void main(String coco[]) {
		try {
			mySchedulerListenerVamosQueVamos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void mySchedulerListenerVamosQueVamos() throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();

		try {
			sched = sf.getScheduler();

			JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("job1").build();

			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("t1").startNow()
					.withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(2)).build();

			sched.getListenerManager().addSchedulerListener(new MySchedulerListener());

			sched.scheduleJob(job, trigger);

			sched.start();

			try {
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			if (sched != null) {
				sched.shutdown();

				SchedulerMetaData metaData = sched.getMetaData();
				log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
			}
		}
	}

	public static void definingPriority() throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();

		try {
			sched = sf.getScheduler();

			JobDetail job = JobBuilder.newJob(TriggerJob.class).withIdentity("job1").build();

			Date date = DateBuilder.futureDate(5, IntervalUnit.SECOND);

			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("TRIGGER_1").startAt(date)
					.withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(2)).withPriority(1).build();

			// if not defined, the priority will go default, with value 5
			Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("TRIGGER_2").startAt(date)
					.withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(2)).forJob(job).build();

			Trigger trigger3 = TriggerBuilder.newTrigger().withIdentity("TRIGGER_3").startAt(date)
					.withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(2)).forJob(job).withPriority(10).build();

			sched.scheduleJob(job, trigger);
			sched.scheduleJob(trigger2);
			sched.scheduleJob(trigger3);

			sched.start();

			try {
				// Thread.sleep(10000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			if (sched != null) {
				// sched.shutdown();

				SchedulerMetaData metaData = sched.getMetaData();
				log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
			}
		}
	}

	/**
	 * Usando a bela da anotação @PersistJobDataAfterExecution, os dados do
	 * jobDataMap se mantem salvo e pode ser alterado, com a execução posterior
	 * possuindo o dado atualizado.<br/>
	 * <br/>
	 * Seriously I should've been using google for translations, but wth, let's do
	 * thissss. Using the @PersistJobDataAfterExecution annotation to save and
	 * persist the data through the beautiful repetion.
	 * 
	 * @throws Exception
	 */
	public static void persistTheJobData() throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();

		try {
			sched = sf.getScheduler();

			JobDetail job = JobBuilder.newJob(PersistPropertyJob.class).withIdentity("job1").build();
			job.getJobDataMap().put(PersistPropertyJob.COUNT, 1);

			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("t1").startNow()
					.withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(2)).build();

			sched.scheduleJob(job, trigger);

			sched.start();

			try {
				// Thread.sleep(10000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			if (sched != null) {
				// sched.shutdown();

				SchedulerMetaData metaData = sched.getMetaData();
				log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
			}
		}
	}

	/**
	 * No arquivo quartz.properties foi definido um xml com todas definições
	 * necessárias. Tive que fazer alguns importações como biblioteca jaxb-api e
	 * gf.javax.transaction.<br/>
	 * <br/>
	 * Inside quartz.properties there is a definition of a XML file with allllll
	 * theeeeeee definitions needed. So do not go cuckoo!!! Or go. Had to import two
	 * libraries: jaxb-api and gf.javax.transaction.
	 * 
	 * @throws Exception
	 */
	public static void readingFromXmlFileAllJobTriggerDefinitions() throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();

		try {
			sched = sf.getScheduler();

			sched.start();

			try {
				// Thread.sleep(10000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			if (sched != null) {
				// sched.shutdown();

				SchedulerMetaData metaData = sched.getMetaData();
				log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
			}
		}
	}

	/**
	 * Se vocÊ esquecer, como eu, de usar modifiedByCalendar, NÃO FUNCIONA.<br/>
	 * <br/>
	 * Do not forget to use modifiedByCalendar() method orelseeeee won't work.
	 * 
	 * @throws Exception
	 */
	public static void ignoringCalendarDates() throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();

		try {
			sched = sf.getScheduler();

			AnnualCalendar funCalendar = new AnnualCalendar();
			Calendar today = new GregorianCalendar(2025, 3, 8);
			funCalendar.setDayExcluded(today, true);

			sched.addCalendar("feriados", funCalendar, true, true);

			Date date = DateBuilder.dateOf(0, 0, 0, 7, 4, 2025);

			JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("job1").build();

			Trigger trigger = TriggerBuilder.newTrigger().startAt(date).withIdentity("t1")
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever())
					.modifiedByCalendar("feriados").build();

			sched.scheduleJob(job, trigger);

			sched.start();

			try {
				// Thread.sleep(10000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			if (sched != null) {
				// sched.shutdown();

				SchedulerMetaData metaData = sched.getMetaData();
				log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
			}
		}
	}

	/**
	 * Implementação da interface InterruptableJob.<br/>
	 * <br/>
	 * IMplementation of the InterruptableJob interfaceeeeeeee. Yes!!!
	 * 
	 * @throws Exception
	 */
	public static void interruptJobImlementation() throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();

		try {
			sched = sf.getScheduler();

			JobDetail job = JobBuilder.newJob(JobInterruptableJob.class).withIdentity("job1").build();

			Trigger trigger = TriggerBuilder.newTrigger().startNow().withIdentity("t1")
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).repeatForever())
					.build();

			sched.scheduleJob(job, trigger);

			sched.start();

			try {
				Thread.sleep(10000);
				sched.interrupt(job.getKey());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			if (sched != null) {
				// sched.shutdown();

				SchedulerMetaData metaData = sched.getMetaData();
				log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
			}
		}
	}

	/**
	 * Com a ajuda do JobExecutionException.setUnscheduleAllTriggers(true).<br/>
	 * <br/>
	 * Unschedule by using JobExecutionException.setUnscheduleAllTriggers(true).
	 * 
	 * @throws Exception
	 */
	public static void unscheduleJobInCaseOfJobException() throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();

		try {
			sched = sf.getScheduler();

			JobDetail job = JobBuilder.newJob(BadJobExceptionBetterRemove.class).withIdentity("job1").build();

			Trigger trigger = TriggerBuilder.newTrigger().startNow().withIdentity("t1")
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
					.build();

			sched.scheduleJob(job, trigger);

			sched.start();

			try {
				// Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			if (sched != null) {
				// sched.shutdown();

				SchedulerMetaData metaData = sched.getMetaData();
				log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
			}
		}
	}

	public static void setFireAgainInsideJobInCaseException() throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();

		try {
			sched = sf.getScheduler();

			JobDetail job = JobBuilder.newJob(BadJobFirstRun.class).withIdentity("job1").build();

			Trigger trigger = TriggerBuilder.newTrigger().startNow().withIdentity("t1")
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
					.build();

			sched.scheduleJob(job, trigger);

			sched.start();

			try {
				// Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			if (sched != null) {
				// sched.shutdown();

				SchedulerMetaData metaData = sched.getMetaData();
				log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
			}
		}
	}

	/**
	 * Testando o job listener, mas também existe o trigger listener que não
	 * pratiquei aqui. Testing the JobListener.<br/>
	 * <br/>
	 * There is also a listener for triggers, just not tested here, but it's kinda
	 * the same.
	 * 
	 * @throws Exception
	 */
	public static void listenerExample() throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();

		try {
			sched = sf.getScheduler();

			JobDetail job = JobBuilder.newJob(SimpleJob1.class).withIdentity("job1", "group456").build();

			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1").startNow().build();

			// configurando o listener
			// setup the listener

			// find job by key
			// pesquisando o job pela chave para adicionar ao listener
			sched.getListenerManager().addJobListener(new Job1Listener(), KeyMatcher.keyEquals(job.getKey()));

			// Rodando todos jobs. Esta linha de código joga uma exceção bem lindona pois já
			// existe um job2 com
			// o mesmo nome criado pela listener
			// Running all jobs stored. This line of code throws an exception as the
			// listener created one already
			// sched.getListenerManager().addJobListener(new Job1Listener(),
			// EverythingMatcher.allJobs());

			// pesquisando o job pelo grupo, o que pode retornar vários. Cuidado.
			// searching by THE job by group, which can make your life full of errors... lol
			// sched.getListenerManager().addJobListener(new Job1Listener(),
			// GroupMatcher.jobGroupEquals("group456"));

			// usando o OR para adicionar vários filtros. Também existe o AND (AndMatcher)
			// using disjunction to have multiples filters. Also we have the conjunction AND
			// (AndMatcher)
//			sched.getListenerManager().addJobListener(new Job1Listener(),
//					OrMatcher.or(KeyMatcher.keyEquals(job.getKey()), GroupMatcher.jobGroupEquals("group456")));

			sched.scheduleJob(job, trigger);

			sched.start();

			try {
				// Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			if (sched != null) {
				// sched.shutdown();

				SchedulerMetaData metaData = sched.getMetaData();
				log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
			}
		}
	}

	public static void delayForJobUsingThreadSleep() throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();

		try {
			sched = sf.getScheduler();

			// This method gives me the date now but 5 seconds ahead
			Date date = DateBuilder.nextGivenSecondDate(null, 15);

			JobDetail job = JobBuilder.newJob(DelayingJob.class).withIdentity("job1")
					.usingJobData(DelayingJob.EXECUTION_DELAY, 10000l).build();

//			Trigger trigger = TriggerBuilder.newTrigger().startAt(date).withIdentity("t1")
//					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).repeatForever())
//					.build();
			Trigger trigger = TriggerBuilder.newTrigger().startAt(date).withIdentity("t1")
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).repeatForever()
							.withMisfireHandlingInstructionNowWithExistingCount())
					.build();

			sched.scheduleJob(job, trigger);

			sched.start();

			try {
				// Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			if (sched != null) {
				// sched.shutdown();

				SchedulerMetaData metaData = sched.getMetaData();
				log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
			}
		}
	}

	/**
	 * Aqui apenas a propriedade estática do job estava sendo mantigo, mas pelo que
	 * li deveria manter também o dado do DataMap. Depois ver com mais calma.<br/>
	 * <br/>
	 * Here only the static property is being incremented properly.
	 * 
	 * @throws Exception
	 */
	public static void jobState() throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();

		try {
			sched = sf.getScheduler();

			// This method gives me the date now but 5 seconds ahead
			Date date = DateBuilder.nextGivenSecondDate(null, 5);

			JobDetail job = JobBuilder.newJob(ColorJob.class).withIdentity("job1").build();
			job.getJobDataMap().put(ColorJob.FAVORITE_COLOR, "Green");
			job.getJobDataMap().put(ColorJob.EXECUTION_COUNT, 1);

			Trigger trigger = TriggerBuilder.newTrigger().startAt(date).withIdentity("t1")
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).withRepeatCount(3))
					.build();

			sched.scheduleJob(job, trigger);

			sched.start();

			try {
				// Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			if (sched != null) {
				// sched.shutdown();

				SchedulerMetaData metaData = sched.getMetaData();
				log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
			}
		}
	}

	public static void misfireTest() throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();

		try {
			sched = sf.getScheduler();

			Calendar c = Calendar.getInstance();
			c.add(Calendar.YEAR, -1);
			Date date = c.getTime();

			JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("job1").build();

			// The misfire instructoin for the SimpleTrigger
			// MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY
			// MISFIRE_INSTRUCTION_FIRE_NOW
			// MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT
			// MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT
			// MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT
			// MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT
			Trigger trigger = TriggerBuilder.newTrigger().startAt(date).withIdentity("t1")
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionIgnoreMisfires())
					.build();

			sched.scheduleJob(job, trigger);

			sched.start();

			try {
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			if (sched != null) {
				// sched.shutdown();

				SchedulerMetaData metaData = sched.getMetaData();
				log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
			}
		}
	}

	public static void runTaksOnlyOnce() throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();

		try {
			sched = sf.getScheduler();
			sched.start();

			JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("job1").storeDurably().build();

			sched.addJob(job, true);
			sched.triggerJob(new JobKey("job1"));

			try {
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			if (sched != null) {
				sched.shutdown();

				SchedulerMetaData metaData = sched.getMetaData();
				log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
			}
		}
	}

	public static void cronTriggerExample() throws SchedulerException {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler = null;

		try {
			scheduler = sf.getScheduler();

			JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("job1").build();
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1")
					.withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")).build();

			// The misfire instruction for CronTrigger are:
			// MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY
			// MISFIRE_INSTRUCTION_DO_NOTHING
			// MISFIRE_INSTRUCTION_FIRE_NOW
			Date d = scheduler.scheduleJob(job, trigger);
			log.info(job.getKey() + " has been scheduled to run at: " + d + " and repeat based on expression: "
					+ trigger.getCronExpression());

			scheduler.start();

			try {
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			if (scheduler != null) {
				// scheduler.shutdown();

				SchedulerMetaData metaData = scheduler.getMetaData();
				log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
			}
		}
	}

	/**
	 * Sobrescrevendo a propriedade do JOB utilizando o da TRIGGER, mas só funciona
	 * SE utilizar o context.getMergedJobDataMap().<br/>
	 * <br/>
	 * You can override the JOB property with the TRIGGER, as long as you have the
	 * same value AND on the class implementation of the job you are using
	 * context.getMergedJobDataMap() (I think the set/get option also works).
	 * 
	 * @throws Exception
	 */
	public static void propertyJob() throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler = null;

		try {
			scheduler = sf.getScheduler();

			scheduler.start();
			JobDetail propJob = JobBuilder.newJob(PropertyTestJob.class)
					.withIdentity(JobKey.jobKey("propJob", "group1")).usingJobData("jobSays", "He-LLO")
					.usingJobData("myFloatValue", 3.14f).storeDurably(false).build();

			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigguer1", "group1")
					.usingJobData("jobSays", "HE-LLO from TRIGGER").startNow().build();

			scheduler.scheduleJob(propJob, trigger);

			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			if (scheduler != null) {
				scheduler.shutdown();

				SchedulerMetaData metaData = scheduler.getMetaData();
				log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
			}
		}
	}

	public static void helloWorld() throws Exception {
		log.info("#### initializing ####");

		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler = sf.getScheduler();

		log.info("#### initialization complete ####");

		Date runTime = dateWithSeconds(5);

		log.info("#### Started scheduler ####");

		scheduler.start();

		JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("job1", "group1").build();

		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startAt(runTime).build();

		scheduler.scheduleJob(job, trigger);

		log.info("#### Waiting 10 seconds... ####");
		try {
			Thread.sleep(16000);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		log.info("#### Shutting down ####");
		scheduler.shutdown(true);
		log.info("#### Shuttdown Complete ####");
	}

	public static void helloWorldShowingKeyJobName() throws Exception {
		log.info("#### initializing ####");

		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler = sf.getScheduler();

		log.info("#### initialization complete ####");

		Date runTime = dateWithSeconds(5);

		log.info("#### Started scheduler ####");

		scheduler.start();

		JobDetail job = JobBuilder.newJob(HelloWithKeyJob.class).withIdentity("job1", "group1").build();

		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startAt(runTime).build();

		scheduler.scheduleJob(job, trigger);

		log.info("#### Waiting 10 seconds... ####");
		try {
			Thread.sleep(16000);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		log.info("#### Shutting down ####");
		scheduler.shutdown(true);
		log.info("#### Shuttdown Complete ####");
	}

	public static void test() throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler = sf.getScheduler();

		Date date = new Date();
		Date startTimeTwoSeconds = dateWithSeconds(2);
		Date startTimeThreeSeconds = dateWithSeconds(3);
		log.info("### starting schedule at: " + date);

		scheduler.start();

		// this job1 will run after 2 seconds
		JobDetail jobDetail = JobBuilder.newJob(HelloWithKeyJob.class).withIdentity("job1", "group1").storeDurably()
				.build();

		scheduler.addJob(jobDetail, true);
		scheduler.triggerJob(JobKey.jobKey("job1", "group1"));

		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		scheduler.shutdown();
	}

	public static void multipleExamples() throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler = sf.getScheduler();

		Date date = new Date();
		Date startTimeTwoSeconds = dateWithSeconds(-2);
		Date startTimeThreeSeconds = dateWithSeconds(3);
		log.info("### starting schedule at: " + date);

		// this job1 will run after 2 seconds
		JobDetail jobDetail = JobBuilder.newJob(HelloWithKeyJob.class).withIdentity("job1", "group1").build();
		// job will run after 2 seconds
		SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
				.startAt(startTimeTwoSeconds).build();

		date = scheduler.scheduleJob(jobDetail, trigger);
		log.info("### " + jobDetail.getKey() + " will run at: " + date + " and repeat " + trigger.getRepeatCount()
				+ " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");

		// this job2 will run after 2 seconds also
		jobDetail = JobBuilder.newJob(HelloWithKeyJob.class).withIdentity("job2", "group1").build();
		trigger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity("trigger2", "group1")
				.startAt(startTimeTwoSeconds).build();

		date = scheduler.scheduleJob(jobDetail, trigger);
		log.info("### " + jobDetail.getKey() + " will run at: " + date + " and repeat " + trigger.getRepeatCount()
				+ " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");

		// this job3 will run 6 times (run once and 1 more times)
		// will be repeating every 1 seconds.
		jobDetail = JobBuilder.newJob(HelloWithKeyJob.class).withIdentity("job3", "group1").build();
		trigger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity("trigger3", "group1")
				.startAt(startTimeThreeSeconds)
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).withRepeatCount(1))
				.build();

		date = scheduler.scheduleJob(jobDetail, trigger);
		log.info("### " + jobDetail.getKey() + " will run at: " + date + " and repeat " + trigger.getRepeatCount()
				+ " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");

		// Only using trigger to start it as the job3 is informed
		trigger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity("trigger3", "group2")
				.startAt(startTimeThreeSeconds)
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).withRepeatCount(1))
				.forJob(jobDetail).build();

		date = scheduler.scheduleJob(trigger);
		log.info("### " + jobDetail.getKey() + " will run at: " + date + " and repeat " + trigger.getRepeatCount()
				+ " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");

		// job4 will run once, 9 seconds in the future
		jobDetail = JobBuilder.newJob(HelloWithKeyJob.class).withIdentity("job4", "group1").build();
		trigger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity("trigger4", "group1")
				.startAt(DateBuilder.futureDate(9, IntervalUnit.SECOND)).build();

		date = scheduler.scheduleJob(jobDetail, trigger);
		log.info("### " + jobDetail.getKey() + " will run at: " + date + " and repeat " + trigger.getRepeatCount()
				+ " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");

		scheduler.start();

		// this job has no trigger by using 'storeDurably' and this kind of
		// code must be put AFTER the start() of the schedule
		jobDetail = JobBuilder.newJob(HelloWithKeyJob.class).withIdentity("job8", "group1").storeDurably().build();
		scheduler.addJob(jobDetail, true);
		scheduler.triggerJob(JobKey.jobKey("job8", "group1"));

		try {
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		scheduler.shutdown();
	}

	public static void helloWorldRepeating() throws Exception {
		log.info("#### initializing ####");

		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler = sf.getScheduler();

		log.info("#### initialization complete ####");

		log.info("#### Scheduling Job ####");

		scheduler.start();

		JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("myJob", "group1").build();

		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger1", "group1").startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever()).build();

		scheduler.scheduleJob(job, trigger);

		log.info("#### Started scheduler ####");

		log.info("#### Waiting 16 seconds... ####");
		try {
			Thread.sleep(16000);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		log.info("#### Shutting down ####");
		scheduler.shutdown();
		log.info("#### Shuttdown Complete ####");
	}

	private static Date dateWithSeconds(int seconds) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.setLenient(true);

		c.set(Calendar.SECOND, c.get(Calendar.SECOND) + seconds);
		c.set(Calendar.MILLISECOND, 0);

		return c.getTime();
	}
}
