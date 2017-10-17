package com.cheguo.pos.util;

import com.min.common.util.EmptyUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by minyangcheng on 2017/8/22.
 */

public class FormatUtil {

    public static String formatRmb(String s, int len) {
        if (s == null || s.length() < 1) {
            return "";
        }
        NumberFormat formater = null;
        double num = Double.parseDouble(s);
        if (len == 0) {
            formater = new DecimalFormat("###,###");

        } else {
            StringBuffer buff = new StringBuffer();
            buff.append("###,###.");
            for (int i = 0; i < len; i++) {
                buff.append("#");
            }
            formater = new DecimalFormat(buff.toString());
        }
        return formater.format(num);
    }

    public static String formatTimeStr(String originalTimeStr, String originalTimeFormat, String destTimeFormt) {
        try {
            SimpleDateFormat originalSdf = new SimpleDateFormat(originalTimeFormat);
            SimpleDateFormat destSdf = new SimpleDateFormat(destTimeFormt);
            Date date = originalSdf.parse(originalTimeStr);
            return destSdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getBeautifulYearMonth(String time) {
        if (EmptyUtils.isEmpty(time)) return "本月";
        String[] timeArr = time.split("-");
        int year = Integer.parseInt(timeArr[0]);
        int month = Integer.parseInt(timeArr[1]);
        Calendar now = Calendar.getInstance();
        int nowYear = now.get(Calendar.YEAR);
        int nowMonth = now.get(Calendar.MONTH) + 1;
        if (year == nowYear) {
            if (month == nowMonth) {
                return "本月";
            } else {
                return month + "月";
            }
        } else {
            return year + "年" + month + "月";
        }
    }

    public static String getYearMonth(String time) {
        return formatTimeStr(time, "yyyy-MM-dd", "yyyy-MM");
    }

    public static String getNowTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public static String getNowTime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    public static String getTransTime(String date, String time) {
        if (EmptyUtils.isEmpty(date) || EmptyUtils.isEmpty(time)) return getNowTimeStamp();
        String s = Calendar.getInstance().get(Calendar.YEAR)+date + " " + time;
        return formatTimeStr(s, "yyyyMMdd HHmmss", "yyyy-MM-dd HH:mm:ss");
    }

}
