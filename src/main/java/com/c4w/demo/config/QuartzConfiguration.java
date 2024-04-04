package com.c4w.demo.config;

import com.c4w.demo.job.MyJobFactory;
import com.c4w.demo.job.MyLogJob;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Configuration
public class QuartzConfiguration {


    @Value("${qz.threadPool.corePoolSize}")
    private int fixedThreadPoolSize;

    @Value("${qz.trigger.repeatInterval}")
    private long repeatInterval;

    @Autowired
    private MyJobFactory jobFactory;

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(fixedThreadPoolSize);
        executor.setThreadNamePrefix("qz-exec-");
        executor.initialize();
        return executor;
    }

    @Bean
    public JobDetailFactoryBean jobDetail() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(MyLogJob.class); // Use your updated job class here
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean
    public SimpleTriggerFactoryBean trigger(JobDetail jobDetail) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(jobDetail);
        trigger.setStartDelay(0L);
        //trigger.setStartTime(); // set start Date time if needed from properties
        trigger.setRepeatInterval(repeatInterval); // every 1 second
        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        return trigger;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(SimpleTriggerFactoryBean trigger) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setJobFactory(jobFactory); // custom SpringJobFactory
        schedulerFactory.setTriggers(trigger.getObject()); // trigger
        schedulerFactory.setTaskExecutor(taskExecutor());
        return schedulerFactory;
    }
}
