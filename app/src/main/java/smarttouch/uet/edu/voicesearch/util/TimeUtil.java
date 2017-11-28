package smarttouch.uet.edu.voicesearch.util;

import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * Created by VanHop on 4/5/2016.
 */
public class TimeUtil {

    private final static int TIME_AROUND = 30;

    /*time format hh:mm
    true if finishingTime > time > startingTime otherwise */
    public static boolean isWithinTimeScale(String time, String startingTime, String finishingTime) {
        String[] timeSplit = time.split(":");
        String[] startingTimeSplit = startingTime.split(":");
        String[] finishingTimeSplit = finishingTime.split(":");

        //compare hh
        if (Integer.parseInt(startingTimeSplit[0]) > Integer.parseInt(timeSplit[0]) || Integer.parseInt(finishingTimeSplit[0]) < Integer.parseInt(timeSplit[0]))
            return false;
        //compare mm
        if (Integer.parseInt(startingTimeSplit[0]) == Integer.parseInt(timeSplit[0]) && Integer.parseInt(startingTimeSplit[1]) > Integer.parseInt(timeSplit[1]) ||
                Integer.parseInt(finishingTimeSplit[0]) == Integer.parseInt(timeSplit[0]) && Integer.parseInt(finishingTimeSplit[1]) < Integer.parseInt(timeSplit[1] ))
            return false;
        return true;
    }

    public static boolean isWithinTimeScaleFilm(String time1, String time2) {
        return isWithinTimeScale(time1,time2, TIME_AROUND);
    }

    public static boolean isWithinTimeScaleTV(String currentTime, String startingTime, String finishingTime) {

        String [] currentTimeSplit = currentTime.split(":");
        String [] startingTimeSplit = startingTime.split(":");
        String [] finishingTimeSplit = finishingTime.split(":");
        int currentHour = Integer.parseInt(currentTimeSplit[0]);
        int currentMinute = Integer.parseInt(currentTimeSplit[1]);
        int startingHour = Integer.parseInt(startingTimeSplit[0]);
        int startingMinute = Integer.parseInt(startingTimeSplit[1]);
        int finishingHour = Integer.parseInt(finishingTimeSplit[0]);
        int finishingMinute = Integer.parseInt(finishingTimeSplit[1]);

        if(((currentHour == startingHour && startingMinute <= currentMinute) ||
                (currentHour > startingHour))&& ((currentHour == finishingHour && currentMinute < finishingMinute)
                ||(currentHour < finishingHour)))
            return true;

        return false;

    }

    public static boolean isWithinTimeScale(String time1, String time2, int timeAround) {
        String[] time1Split = time1.split(":");
        String[] time2Split = time2.split(":");
        int time1Hour = Integer.parseInt(time1Split[0].trim());
        int time1Minute = Integer.parseInt(time1Split[1].trim());
        int time2Hour = Integer.parseInt(time2Split[0].trim());
        int time2Minute = Integer.parseInt(time2Split[1].trim());

        //within 1h
        if (Math.abs(time1Hour - time2Hour) > 1)
            return false;
        if (time1Hour - time2Hour == 0 && Math.abs(time1Minute - time2Minute) <= timeAround)
            return true;
        if (time1Hour > time2Hour && time1Minute + 60 - time2Minute <= timeAround)
            return true;
        if (time1Hour < time2Hour && time2Minute + 60 - time1Minute <= timeAround)
            return true;

        return false;

    }

    private static boolean isMorning(String sentence) {
        return sentence.contains("sáng") ? true : false;
    }

    private static boolean isAfternoon(String sentence) {
        return sentence.contains("chiều") ? true : false;
    }

    private static boolean isEvening(String sentence) {
        return sentence.contains("tối") ? true : false;
    }

    private static boolean isMidday(String sentence) {
        return sentence.contains("trưa") ? true : false;
    }

    public static boolean isRightNow(String sentence){
        return sentence.contains("đang chiếu") || sentence.contains("đang phát") || sentence.contains("đang được") ||
                sentence.contains("bây giờ") || sentence.contains("hiện tại") || sentence.contains("hiện giờ") ? true : false;
    }

    public static String[] getTimeScale(String sentence) {

        String [] timeScale = new String[2];
        timeScale[0] = "00:00";
        timeScale[1] = "24:00";

        if (isMorning(sentence)) {
            timeScale[0] = "00:00";
            timeScale[1] = "12:00";
        } else if (isMidday(sentence)) {
            timeScale[0] = "11:00";
            timeScale[1] = "13:00";
        } else if (isAfternoon(sentence)) {
            timeScale[0] = "12:00";
            timeScale[1] = "18:00";
        } else if (isEvening(sentence)) {
            timeScale[0] = "18:00";
            timeScale[1] = "24:00";
        } else if( isRightNow(sentence)){
            Date date = new Date();
            timeScale[0] = StringUtils.leftPad(String.valueOf(date.getHours()),2,"0") + ":" + StringUtils.leftPad(String.valueOf(date.getMinutes()),2,"0");
            timeScale[1] = timeScale[0];
        }

        return timeScale;
    }

    // dd/mm/yy
    public static String nextDateTV(String date){

        String [] tokens = date.split("/");
        int day = Integer.parseInt(tokens[0]);
        int month = Integer.parseInt(tokens[1]);
        int year = Integer.parseInt(tokens[2]);

        boolean isLeapYear = year%4 == 0 && year%100 != 0 || year%400 == 0 ? true : false;

        if(day == 31 && month == 12){
            return "01/01/" + (year + 1);
        } else if(isLeapYear && day == 28 && month == 2){
            return "29/02/" + (year);
        } else if((day == 30 && (month == 4 || month == 6 || month == 9 || month == 11)) || (day == 28 && month == 2) ||
                (day == 31 && (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10))){
            return "01/" + StringUtils.leftPad(String.valueOf(month+1),2,"0") + "/" + year;
        }

        return StringUtils.leftPad(String.valueOf(day + 1),2,"0") + "/" + StringUtils.leftPad(String.valueOf(month),2,"0") + "/" + year;

    }

    public static String lateDateTV(String date){

        String [] tokens = date.split("/");
        int day = Integer.parseInt(tokens[0]);
        int month = Integer.parseInt(tokens[1]);
        int year = Integer.parseInt(tokens[2]);

        boolean isLeapYear = year%4 == 0 && year%100 != 0 || year%400 == 0 ? true : false;

        if(day == 1 && month == 1){
            return "31/12/" + (year - 1);
        } else if(isLeapYear && day == 1 && month == 3){
            return "29/02/" + (year);
        } else if(!isLeapYear && day == 1 && month == 3){
            return "28/02/" + year;
        } else if((day == 1 && (month == 4 || month == 6 || month == 9 || month == 11))){
            return "31/" + StringUtils.leftPad(String.valueOf(month+1),2,"0") + "/" + year;
        } else if((day == 1 && (month == 12 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10))){
            return "30/" + StringUtils.leftPad(String.valueOf(month+1),2,"0") + "/" + year;
        }

        return StringUtils.leftPad(String.valueOf(day - 1),2,"0") + "/" + StringUtils.leftPad(String.valueOf(month),2,"0") + "/" + year;

    }

    // dd-mm-yy
    public static String nextDateCinema(String date){
        return "";
    }

    public static int compareTime(String time1,String time2){
        String [] time1Split = time1.split(":");
        String [] time2Split = time2.split(":");
        if(Integer.parseInt(time1Split[0]) != Integer.parseInt(time2Split[0]))
            return Integer.parseInt(time1Split[0]) - Integer.parseInt(time2Split[0]);
        return Integer.parseInt(time1Split[1]) - Integer.parseInt(time2Split[1]);
    }
}