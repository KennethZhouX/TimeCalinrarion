package com.timecheck;

import com.timecheck.Model.Config;
import com.timecheck.Task.TimeCheckTask;
import com.timecheck.Util.ConfigUtil;

import java.util.Timer;

public class CheckTimeApplication {

    public static void main(String args[]) {
        Config config = ConfigUtil.getConfig();
        Timer timer = new Timer();
        timer.schedule(new TimeCheckTask(), 1000, config.getTimeCheckTaskPeriod());//在1秒后执行此任务,每次间隔2秒执行一次,如果传递一个Data参数,就可以在某个固定的时间执行这个任务.
    }
}