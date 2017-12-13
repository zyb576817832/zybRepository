package com.ebon.job.test.service;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class HelloScheduling {

	public static void main(String[] args) {
//		try {
////			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
////			
////			JobDetail jobDetail = new JobDetail("helloWorldJob",
////					Scheduler.DEFAULT_GROUP, Test.class);
////
////			Trigger trigger = new SimpleTrigger("simpleTrigger",
////					Scheduler.DEFAULT_GROUP, new Date(), null,
////					SimpleTrigger.REPEAT_INDEFINITELY, 1000);
////
////			scheduler.scheduleJob(jobDetail, trigger);
//			
////			scheduler.start();
//			
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}
	}

}
