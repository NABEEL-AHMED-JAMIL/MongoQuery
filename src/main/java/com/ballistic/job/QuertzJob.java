package com.ballistic.job;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;;

public class QuertzJob {

    public static void main(String args[]) throws SchedulerException {

        // Grab the Scheduler instance from the Factory
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        JobDetail job = newJob(MyJob.class).
            withIdentity("job1", "group1").
            build();

        Trigger trigger = newTrigger().
                withIdentity("trigger1", "group1").
                startNow().withSchedule(simpleSchedule()
                .withIntervalInSeconds(40).repeatForever()).build();
        scheduler.scheduleJob(job, trigger);
        scheduler.start();
    }
}
