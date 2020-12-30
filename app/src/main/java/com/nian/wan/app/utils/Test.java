package com.nian.wan.app.utils;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mark on 2018/1/16.
 */

public class Test {

    public static void main(String []args){
         TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Oh");
            }
        };
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        pool.scheduleWithFixedDelay(task, 0,2100, TimeUnit.MILLISECONDS);

    }
}
