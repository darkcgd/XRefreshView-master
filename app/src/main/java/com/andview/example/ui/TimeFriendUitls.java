package com.andview.example.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by add on 2017/5/9.
 */

public class TimeFriendUitls {
    /**
     * 显示时间，如果与当前时间差别小于一天，则自动用**秒(分，小时)前，如果大于一天则用format规定的格式显示
     *
     * @return
     * @author wxy
     */
    public static String showTime(String time) {
        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = "yyyy-MM-dd HH:mm:ss";
        String r = "";
        Date ctime = null;
        try {
            ctime = dff.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ctime == null) return r;

        long nowtimelong = System.currentTimeMillis();

        long ctimelong = ctime.getTime();
        long result = Math.abs(nowtimelong - ctimelong);

        if (result < 60000) {// 一分钟内
            long seconds = result / 1000;
            if (seconds == 0) {
                r = "刚刚";
            } else {
                r = seconds + "秒前";
            }
        } else if (result >= 60000 && result < 3600000) {// 一小时内
            long seconds = result / 60000;
            r = seconds + "分钟前";
        } else if (result >= 3600000 && result < 86400000) {// 一天内
            long seconds = result / 3600000;
            r = seconds + "小时前";
        } else if (result >= 86400000 && result < 1702967296) {// 三十天内
            long seconds = result / 86400000;
            r = seconds + "天前";
        } else {// 日期格式
            format = "MM-dd HH:mm";
            SimpleDateFormat df = new SimpleDateFormat(format);
            r = df.format(ctime).toString();
        }
        return r;
    }

    /*获取今天的日期*/
    public static String getToady() {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 0);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);

        return dateString;
    }

    /*获取今天标准的指定的日期*/
    public static String getToadytime(int index) {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, index);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }

    public static int checkDate(String today, String last) {
        int result = 0;

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(today);
            Date dt2 = df.parse(last);
            if (dt1.getTime() - dt2.getTime() == 0) {
                return result = 1;/*今天*/
            } else if (dt1.getTime() - dt2.getTime() == 86400000) {
                return result = 2;/*昨天*/
            } else if (dt1.getTime() - dt2.getTime() >= (86400000 * 2)) {
                return result = 3;/*前天*/
            } else if (dt1.getTime() - dt2.getTime() >= (86400000 * 3)) {
                return result = 4;/*三天前*/
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    /*将秒换成分钟，小时，处理*/
    public static String minute(int seconds) {
        int second, minute, hour;
        second = seconds % 60;
        minute = (seconds / 60) % 60;
        hour = seconds / 3600;
        /*说明有分钟*/
        if (hour > 0) {
            if (second < 10) {
                return hour + ":" + minute + ":0" + second;
            } else {
                return hour + ":" + minute + ":" + second;
            }
        } else if (minute > 0) {
            if (second < 10) {
                return minute + ":0" + second;
            } else {
                return minute + ":" + second;
            }
        } else if (second > 0) {
            if (second < 10) {
                return minute + ":0" + second;
            } else {
                return minute + ":" + second;
            }

        } else {
            return "0";
        }

    }

}
