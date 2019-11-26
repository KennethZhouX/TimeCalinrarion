package com.timecheck.Model;

import com.sun.javafx.image.IntPixelGetter;

public class Config {
    private String targetTimeMachineIp;
    private Integer permitTimeInMinutes;
    private Integer timeCheckTaskPeriod;

    public String getTargetTimeMachineIp() {
        return targetTimeMachineIp;
    }

    public void setTargetTimeMachineIp(String timeMachineIp) {
        this.targetTimeMachineIp = timeMachineIp;
    }

    public Integer getPermitTimeInMinutes() {
        return permitTimeInMinutes;
    }

    public void setPermitTimeInMinutes(Integer permitTimeInMinutes) {
        this.permitTimeInMinutes = permitTimeInMinutes;
    }

    public Integer getTimeCheckTaskPeriod() {
        return timeCheckTaskPeriod;
    }

    public void setTimeCheckTaskPeriod(Integer timeCheckTaskPeriod) {
        this.timeCheckTaskPeriod = timeCheckTaskPeriod;
    }
}
