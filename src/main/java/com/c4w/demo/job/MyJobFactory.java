package com.c4w.demo.job;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Service;

@Service
public class MyJobFactory extends AdaptableJobFactory {
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) {
        return applicationContext.getBean(bundle.getJobDetail().getJobClass());
    }
}
