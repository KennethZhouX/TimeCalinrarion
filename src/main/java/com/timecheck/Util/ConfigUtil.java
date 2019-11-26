package com.timecheck.Util;

import com.timecheck.CheckTimeApplication;
import com.timecheck.Model.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class ConfigUtil {
    private static final Logger log = LogManager.getLogger(ConfigUtil.class);
    public static Config getConfig() {
        InputStream input = null;

        Properties prop = new Properties();
        String propFileName = "config.properties";

        input = ConfigUtil.class.getClassLoader().getResourceAsStream(propFileName);
        Config config = new Config();
        if (input != null) {
            try {
                prop.load(input);
                int available = input.available();
                System.out.println("Available: " + available);
                config.setTargetTimeMachineIp(prop.getProperty("target_machine_ip"));
                config.setPermitTimeInMinutes(Integer.parseInt(StringUtil.replaceWhiteSpace(prop.getProperty("permit_time_in_minutes"))));
                config.setTimeCheckTaskPeriod(getPeriodTime(prop.getProperty("time_check_task_period")));

                input.close();
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }
        }
        return config;
    }

    private static Integer getPeriodTime(String periodStr) {
        String [] items = periodStr.split("\\*");
        int periodTime = 1;
        for (String time : items) {
            int timeItem = Integer.parseInt(StringUtil.replaceWhiteSpace(time));
            if (timeItem <= 0) {
                log.error("时间不能小于或等于0");
                break;
            }
            periodTime = periodTime * timeItem;
        }

        return periodTime;
    }
}
