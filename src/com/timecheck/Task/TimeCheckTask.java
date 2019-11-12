package com.timecheck.Task;

import com.timecheck.Common.BaseCommand;
import com.timecheck.Util.LinuxCmdUtil;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.apache.commons.net.ntp.TimeStamp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeCheckTask extends java.util.TimerTask{

    private static final Logger log = LogManager.getLogger(TimeCheckTask.class);
    private String DEFAULT_COMPUTER_USER = "ROOT";
    private String SYSTEM_USER_PASSWORD = "P@ssw0rd1";
    private String TIME_BJ_TARGET_IP = "117.187.129.208";
    private int PERMIT_TIME_IN_MINUTES = 1;

    @Override
    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
        log.info("【START】开始校验系统时间：");
        Date currentMachineDate = getCurrentMachineDate();
        log.info("当前系统时间: " + sdf.format(currentMachineDate));
//        Date targetDate = getTargetDate(timeSyncConfig.getTargetIp());
        Date targetDate = getTargetDate(TIME_BJ_TARGET_IP);
        log.info("目标机器("+TIME_BJ_TARGET_IP+")时间：" + sdf.format(targetDate));
        int timeDiff = getBetweenMinutes(currentMachineDate, targetDate);
        if(timeDiff >= PERMIT_TIME_IN_MINUTES) {
            setCurrentMachineDate(currentMachineDate, targetDate, timeDiff);
            log.info("当前系统时间有误差，已更新至目标机器"+TIME_BJ_TARGET_IP+"时间");
        } else {
            log.info("当前系统时间正常");
        }

        log.info("【END】校验结束");
    }

    public Date getTargetDate(String targetIp) {
        try {
            NTPUDPClient timeClient = new NTPUDPClient();
            InetAddress timeServerAddress = InetAddress.getByName(targetIp);
            TimeInfo timeInfo = timeClient.getTime(timeServerAddress);
            TimeStamp timeStamp = timeInfo.getMessage().getTransmitTimeStamp();
            Date date = timeStamp.getDate();
            System.out.println(date);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            return date;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Date getCurrentMachineDate() {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
        Date date = new Date();// 获取当前时间
        System.out.println("现在时间：" + sdf.format(date)); // 输出已经格式化的现在时间（24小时制）
        return date;
    }

    public void setCurrentMachineDate(Date currentMachineDate, Date targetMachineDate, int diffMinutes) {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
        String command = "date -s \"" + sdf.format(targetMachineDate) +"\"";
        String syncCommand = "hwclock -w";
        log.info("当前系统时间: " +sdf.format(currentMachineDate)+ "和北京时间:" +sdf.format(targetMachineDate)+"相差:" + diffMinutes + "分钟" + "开始更新系统时间");
        if(! isRootUser()) {
            command = String.format(BaseCommand.USER_VERIFICATION, SYSTEM_USER_PASSWORD) + " " + command;
            syncCommand = String.format(BaseCommand.USER_VERIFICATION, SYSTEM_USER_PASSWORD) + " " + syncCommand;
            //更正系统时间
            log.info("更正系统时间：[COMMAND:"+command+"]");
            String result = LinuxCmdUtil.executeLinuxCmdWithPipeCharacter(command);
            log.info("当前系统时间为：" + getCurrentMachineDate());
            log.info("更正硬件时间： [COMMAND:"+syncCommand+"]");
            LinuxCmdUtil.executeLinuxCmdWithPipeCharacter(syncCommand);
        } else {
            log.info("更正系统时间：[COMMAND:"+command+"]");
            String result = LinuxCmdUtil.executeLinuxCmd(command);
            log.info("当前系统时间为：" + getCurrentMachineDate());
            log.info("更正硬件时间： [COMMAND:"+syncCommand+"]");
            LinuxCmdUtil.executeLinuxCmd(syncCommand);
        }


        //将系统时间同步到硬件

    }

    /**
     * 两个时间之差
     * @param startDate
     * @param endDate
     * @return 分钟
     */
    public static Integer getBetweenMinutes(Date startDate, Date endDate) {
        Integer minutes = 0;
        try {
            if(startDate!=null&&endDate!=null) {
                long ss = 0;
                if(startDate.before(endDate)) {
                    ss = endDate.getTime() - startDate.getTime();
                }else {
                    ss = startDate.getTime() - endDate.getTime();
                }
                minutes = Integer.valueOf((int) (ss/(60*1000))) ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return minutes;
    }

    /**
     *  current computer is root?
     * @return
     */
    private boolean isRootUser() {
        String currentUser =  LinuxCmdUtil.executeLinuxCmd(BaseCommand.WHO_AM_I);
        if(DEFAULT_COMPUTER_USER.equals(currentUser.toUpperCase())) {
            return true;
        }
        return false;
    }
}
