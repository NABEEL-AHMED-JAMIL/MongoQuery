package com.ballistic.kafka.jobs;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class KCronJobs {

    enum JobFunction {
        START_C, STOP_C, START_P, STOP_P,
        FETCH_DB_DATA, DELETE_DB_DATA,
        UPDATE_LUCENCE_DATA, DELETE_LUCENCE_DATA
    }

    /**
     * job-name -> wo dyna hei dyna hai
     * method -> call or process call wo hu dy gay
     * */

    /**
     * Cron Job Fetch Date From DB after 1 Mint and Update Lucene Search-Eng
     * */
    public void fetchDBDateModifyLucene() {}

    public static void main(String args[]) {
        try {
            // Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // and start it off
            scheduler.start();
            System.out.println("Pakistan zindabad");
            //scheduler.shutdown();

        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }
}
