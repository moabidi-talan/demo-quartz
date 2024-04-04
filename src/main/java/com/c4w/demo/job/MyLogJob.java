package com.c4w.demo.job;

import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyLogJob implements Job {

    @Autowired
    private MyLogService myLogService;//dependency injection


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        myLogService.log("Running...");
    }
}
