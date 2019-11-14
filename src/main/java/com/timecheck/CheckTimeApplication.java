package com.timecheck;

import com.timecheck.Task.TimeCheckTask;

import java.util.Timer;

public class CheckTimeApplication {

    public static void main(String args[]) {
//        int period = 60 * 1000 * 60; //一小时检查一次
        int period = 60 * 1000;
        Timer timer = new Timer();
        timer.schedule(new TimeCheckTask(), 1000, period);//在1秒后执行此任务,每次间隔2秒执行一次,如果传递一个Data参数,就可以在某个固定的时间执行这个任务.
    }
}
