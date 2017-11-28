package smarttouch.uet.edu.voicesearch.util;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * Created by VanHop on 4/7/2016.
 */
public class DatetimeUtil {

    private static final String TAG = "VAV_Datetime";

    private static boolean mapCreated = false;
    private static Map<String,Integer> dayMap;
    private static Map<String,Integer> absoluteDayMap;

    private static void createMap(){
        dayMap = new HashMap<>();
        dayMap.put("nay", 0);
        dayMap.put("mai", 1);
        dayMap.put("ngày kia", 2);

        absoluteDayMap = new HashMap<>();
        absoluteDayMap.put("chủ nhật", 1);
        absoluteDayMap.put("thứ hai", 2);
        absoluteDayMap.put("thứ 2", 2);
        absoluteDayMap.put("thứ ba", 3);
        absoluteDayMap.put("thứ 3", 3);
        absoluteDayMap.put("thứ tư", 4);
        absoluteDayMap.put("thứ 4", 4);
        absoluteDayMap.put("thứ năm", 5);
        absoluteDayMap.put("thứ 5", 5);
        absoluteDayMap.put("thứ sáu", 6);
        absoluteDayMap.put("thứ 6", 6);
        absoluteDayMap.put("thứ bảy", 7);
        absoluteDayMap.put("thứ 7", 7);
    }

    public static String convertDate2Str(GregorianCalendar date){
        return StringUtils.leftPad(String.valueOf(date.getTime().getDate()), 2, "0") + "/" + StringUtils.leftPad(String.valueOf((date.getTime().getMonth() + 1)), 2, "0") + "/" + (2000 + date.getTime().getYear() - 100);
    }

    public static String getDatetimePhrase(String sentence, List<String> tags) {
        if (!mapCreated) {
            createMap();
            mapCreated = true;
        }

        List<String> datetime = new ArrayList<>();
        List<String> tokens = StrUtil.tokenizeString(sentence);
        for (int i = 0; i < tags.size(); i++) {
            if (tags.get(i).contains("datetime")) {
                datetime.add(tokens.get(i));
            }
        }

        String datetimePhrase = StrUtil.join(datetime);
        datetimePhrase = datetimePhrase.replace("một", "1");
        datetimePhrase = datetimePhrase.replace("hai", "2");
        datetimePhrase = datetimePhrase.replace("ba", "3");
        datetimePhrase = datetimePhrase.replace("bốn", "4");
        datetimePhrase = datetimePhrase.replace("tư", "4");
        datetimePhrase = datetimePhrase.replace("năm", "5");
        datetimePhrase = datetimePhrase.replace("sáu", "6");
        datetimePhrase = datetimePhrase.replace("bảy", "7");
        datetimePhrase = datetimePhrase.replace("tám", "8");
        datetimePhrase = datetimePhrase.replace("chín", "9");

//        datetimePhrase = datetimePhrase.replace("phút", "");
//        datetimePhrase = datetimePhrase.replace(" ", "");

        if (datetimePhrase.contains("mươi")) {
            if (datetimePhrase.endsWith("mươi"))
                datetimePhrase = datetimePhrase.replace("mươi", "0");
            else
                datetimePhrase = datetimePhrase.replace("mươi", "");
        }

        if (datetimePhrase.contains("mười")) {
            if (datetimePhrase.endsWith("mười"))
                datetimePhrase = datetimePhrase.replace("mười", "10");
            else
                datetimePhrase = datetimePhrase.replace("mười", "1");
        }

        return datetimePhrase;
    }

    public static int getCurrentHour() {
        GregorianCalendar date = new GregorianCalendar();
        return date.get(Calendar.HOUR_OF_DAY);
    }

    public static List<String> getTime(String sentence, boolean isNoon, boolean isNight) {
        if (!mapCreated) {
            createMap();
            mapCreated = true;
        }

        List<String> time = new ArrayList<>();
        String hourPhrase = null;
        String minutePhrase = null;

        Pattern pattern = Pattern.compile(Regex.TIME_HOUR);
        Matcher matcher = pattern.matcher(sentence);
        if (matcher.find()) {
            hourPhrase = matcher.group(1);
//            Log.d(TAG, "hourPhrase: " + hourPhrase);
        }

        pattern = Pattern.compile(Regex.TIME_MINUTE);
        matcher = pattern.matcher(sentence);
        if (matcher.find()) {
            minutePhrase = matcher.group().replace(" ", "");
//            Log.d(TAG, "minutePhrase: " + minutePhrase);
        }

        //  Don't have time in input
        if (hourPhrase == null && minutePhrase == null) {
            return time;
        }

        if (hourPhrase != null) {
            time.add(0, hourPhrase);
        } else {
            time.add(0, "0");
        }
        time.add(1, "0");

        if (minutePhrase != null) {
            pattern = Pattern.compile(Regex.ONLY_NUMBER);
            matcher = pattern.matcher(minutePhrase);
            if (matcher.find()) {
                time.set(1, matcher.group());
            } else if (minutePhrase.contains("r")) {
                time.set(1, "30");
            }
        }

        if (hourPhrase != null) {
            int hour = Integer.parseInt(time.get(0));
            if (isNight && hour < 4) {
                //  do nothing
            } else if ((isNoon || isNight) && hour < 13) {
                if (hour == 12) {
                    hour = 0;
                } else {
                    hour += 12;
                }
            }

            if (sentence.contains("kém")) {
                if (hour > 0)
                    hour -= 1;
                else
                    hour = 23;

                int minute = 60 - Integer.parseInt(time.get(1));
                time.set(1, Integer.toString(minute));
            }

            time.set(0, String.valueOf(hour));
        }

        String nextTimeWords[] = {"sau", "nữa", "tới"};
        for (String word : nextTimeWords) {
            if (sentence.contains(word)) {
                GregorianCalendar currentDate = new GregorianCalendar();

                int currentHour = currentDate.get(Calendar.HOUR_OF_DAY);
                int currentMinute = currentDate.get(Calendar.MINUTE);

                int addedMinute = Integer.parseInt(time.get(1)) + currentMinute;
                int addedHour = Integer.parseInt(time.get(0)) + currentHour + addedMinute / 60;

                time.set(0, String.valueOf(addedHour % 24));
                time.set(1, String.valueOf(addedMinute % 60));

                return time;
            }
        }

        int hour = Integer.parseInt(time.get(0));
        int minute = Integer.parseInt(time.get(1));
        time.set(0, String.valueOf((hour + minute / 60) % 24));
        time.set(1, String.valueOf(minute % 60));

        return time;
    }

    public static ArrayList<Integer> getDay(String sentence) {
        if (!mapCreated) {
            createMap();
            mapCreated = true;
        }

        ArrayList<Integer> days = new ArrayList<>();
        if (sentence.contains("hàng ngày") || sentence.contains("mỗi ngày")) {
            for(int i = 1; i < 8; i++){
                days.add(i);
            }
            return days;
        }

        int currentDay = (new GregorianCalendar()).get(DAY_OF_WEEK);
        for (String key : dayMap.keySet()) {
            if (sentence.contains(key)) {
                int newDay = currentDay + dayMap.get(key);
                if (newDay > 7) newDay -= 7;
                days.add(newDay);   //  alarm requires day values from 1 -> 7
                return days;
            }
        }

        for (String key : absoluteDayMap.keySet()) {
            if (sentence.contains(key)) {
                int newDay = absoluteDayMap.get(key);
                days.add(newDay);
                return days;
            }
        }

        return days;
    }

    public static ArrayList<Integer> getDayForAlarm(String sentence, int setAlarmHour) {
        ArrayList<Integer> days = getDay(sentence);

        //  check hour for no need to repeat alarm
        int currentHour = (new GregorianCalendar()).get(Calendar.HOUR_OF_DAY);
        if (days.size() == 1 && setAlarmHour < currentHour) {
            days.clear();
        }
        return days;
    }

    public static GregorianCalendar getDate(String sentence) {

        if(sentence.length() <= 2 && sentence.matches("\\d{1,2}")){
            GregorianCalendar date = new GregorianCalendar();
            date.set(Calendar.DAY_OF_MONTH, Integer.parseInt(sentence));
            return date;
        }

        if (!mapCreated) {
            createMap();
            mapCreated = true;
        }
        GregorianCalendar date = new GregorianCalendar();
        Date date2 = new Date();
        int weekenDate = 7 - date2.getDay();
        if(sentence.contains("cuối tuần")){
            date.add(Calendar.DAY_OF_MONTH,weekenDate);
            if(sentence.contains("sau"))
                date.add(Calendar.DAY_OF_MONTH,7);
            return date;
        }


        if (sentence.contains("hôm qua")) {
            date.add(Calendar.DAY_OF_MONTH, -1);
            return date;
        } else if (sentence.contains("hôm trước")) {
            date.add(Calendar.DAY_OF_MONTH, -2);
            return date;
        }

        ArrayList<Integer> days = getDay(sentence);

        if (days.size() == 1) {
            date = day2Date(days.get(0), isNextWeek(sentence));
        } else {
            //  getDate
            Pattern pattern = Pattern.compile(Regex.FULL_DATE);
            Matcher matcher = pattern.matcher(sentence);
            if (matcher.find()) {
                if (matcher.group(3) != null)
                    date.set(DATE, Integer.parseInt(matcher.group(3)));

                if (matcher.group(5) != null)
                    if (matcher.group(5).equals("tư"))
                        date.set(MONTH, 3);
                    else
                        date.set(MONTH, Integer.parseInt(matcher.group(5)) - 1);

                if (matcher.group(8) != null)
                    date.set(YEAR, Integer.parseInt(matcher.group(8)));
            }
        }

        return date;
    }

    public static GregorianCalendar getDateToConvert(String sentence) {
        if (!mapCreated) {
            createMap();
            mapCreated = true;
        }

        GregorianCalendar date = new GregorianCalendar();

        if (sentence.contains("hôm qua")) {
            date.add(Calendar.DAY_OF_MONTH, -1);
            return date;
        } else if (sentence.contains("hôm trước")) {
            date.add(Calendar.DAY_OF_MONTH, -2);
            return date;
        }

        ArrayList<Integer> days = getDay(sentence);

        if (days.size() == 1) {
            return day2Date(days.get(0), isNextWeek(sentence));
        } else {
            //  getDate
            Pattern pattern = Pattern.compile(Regex.FULL_DATE);
            Matcher matcher = pattern.matcher(sentence);
            if (matcher.find()) {
                if (matcher.group(3) != null)
                    date.set(DATE, Integer.parseInt(matcher.group(3)));

                if (matcher.group(5) != null)
                    if (matcher.group(5).equals("tư"))
                        date.set(MONTH, 3);
                    else
                        date.set(MONTH, Integer.parseInt(matcher.group(5)) - 1);

                if (matcher.group(8) != null)
                    date.set(YEAR, Integer.parseInt(matcher.group(8)));

                return date;
            }
        }

        return null;
    }

    public static GregorianCalendar day2Date(int day, boolean isNextWeek) {
        GregorianCalendar date = new GregorianCalendar();
        GregorianCalendar currentDate = new GregorianCalendar();
        int currentDay = currentDate.get(DAY_OF_WEEK);
        int delta = day - currentDay;

        int currentDayOfMonth = currentDate.get(DAY_OF_MONTH);
        int dayOfMonth = currentDayOfMonth + delta;
        if (delta < 0 || isNextWeek) dayOfMonth += 7;   // next week

        int currentMonth = currentDate.get(MONTH);
        int currentYear = currentDate.get(YEAR);
        if (dayOfMonth < 1) {
            if (currentMonth == 0) {
                int year = currentYear - 1;
                date.set(YEAR, year);
                date.set(MONTH, 11);
            } else {
                int month = currentMonth - 1;
                date.set(MONTH, month);
            }
            dayOfMonth = date.getActualMaximum(DAY_OF_MONTH);
            date.set(DATE, dayOfMonth);
        } else if (dayOfMonth > currentDate.getActualMaximum(DAY_OF_MONTH)) {
            if (currentMonth == 11) {
                int year = currentYear + 1;
                date.set(YEAR, year);
                date.set(MONTH, 0);
            } else {
                int month = currentMonth + 1;
                date.set(MONTH, month);
            }
            dayOfMonth -= currentDate.getActualMaximum(DAY_OF_MONTH);
            date.set(DATE, dayOfMonth);
        } else {
            date.set(DATE, dayOfMonth);
        }

        return date;
    }

    public static boolean isMorning(String sentence) {
        String morningWords[] = {"sáng", "trưa"};
        for (String word : morningWords) {
            if (sentence.contains(word)) return true;
        }
        return false;
    }

    public static boolean isNoon(String sentence) {
        String noonWords[] = {"chiều", "tối", "đêm"};
        for (String word : noonWords) {
            if (sentence.contains(word)) return true;
        }
        return false;
    }

    public static boolean isNight(String sentence) {
        String nightWords[] = {"tối", "đêm", "khuya"};
        for (String word : nightWords) {
            if (sentence.contains(word)) return true;
        }
        return false;
    }

    public static boolean isNextWeek(String sentence) {
        String nextWeekWords[] = {"tuần sau", "tuần tới"};
        for (String word : nextWeekWords) {
            if (sentence.contains(word)) return true;
        }

        return false;
    }

    public static boolean isToday(GregorianCalendar date) {
        GregorianCalendar today = new GregorianCalendar();
        if (date.get(DATE) == today.get(DATE) && date.get(MONTH) == today.get(MONTH) && date.get(YEAR) == today.get(YEAR))
            return true;

        return false;
    }

    public static String getDayOfWeek(GregorianCalendar date) {
        switch (date.get(DAY_OF_WEEK)) {
            case 1:
                return "Chủ nhật";
            case 2:
                return "Thứ hai";
            case 3:
                return "Thứ ba";
            case 4:
                return "Thứ tư";
            case 5:
                return "Thứ năm";
            case 6:
                return "Thứ sáu";
            case 7:
                return "Thứ bảy";
            default:
                return null;
        }
    }

}
