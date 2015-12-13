package app.newbee.lib.util;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressLint("SimpleDateFormat")
public class TimeUtils {
    public static final String FMT = "yyyy-MM-dd HH:mm:ss";
    public static final String _FMT = "yyyy-MM-dd HH:mm";
    public static final String YM = "yyyy-MM";
    public static final String C_FMT = "yyyy年MM月dd日HH时mm分ss秒";
    public static final String L_FMT = "yyyyMMddHHmmssSSS";
    public static final String L_DATE_FMT = "yyyyMMdd";
    public static final String DATE_FMT = "yyyy-MM-dd";
    public static final String DATE_FMT2 = "yyyy年MM月dd日";
    public static final String DATE_FMT3 = "yyyy.MM.dd";
    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    public static void main(String[] args) {
        System.out.println(getFormatDate("HH:mm:ss"));
    }

    /**
     * @param format 指定的日期格式<br>
     *               eg:<br>
     *               "yyyy-MM-dd HH:mm:ss"<br>
     *               "yyyy-MM-dd"<br>
     *               "yyyyMMddHHmmss"<br>
     *               "HH:mm:ss"<br>
     *
     * @return
     */
    public static String getFormatDate(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    public static String getTimeStamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        long timeMilllis = System.currentTimeMillis();
        return simpleDateFormat.format(timeMilllis);
    }

    public static String getUnixTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * 将long类型的日期转化为需要日期格式
     *
     * @param date
     *
     * @return
     */
    public static String getDateStringForLong(long date, String FMT) {
        Date Datz = getDateByLong(date);
        SimpleDateFormat sdf = new SimpleDateFormat(FMT);
        return sdf.format(Datz);
    }

    /**
     * 将数据库中的Long类型时间转换为date
     *
     * @param date
     *
     * @return
     */
    public static Date getDateByLong(long date) {
        SimpleDateFormat sf = new SimpleDateFormat(L_FMT);
        try {
            return sf.parse(date + "");
        } catch (ParseException e) {
        }
        return new Date();
    }

    /**
     * 将long类型的日期转化为需要日期格式
     *
     * @param date
     *
     * @return
     */
    public static String getDateStringForLongForUnix(long date, String FMT) {
        Date Datz = new Date(date * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat(FMT);
        return sdf.format(Datz);
    }

    /**
     * 将long类型的日期转化为需要日期格式
     *
     * @param date
     *
     * @return
     */
    public static String getDateStringForLongForUnix(String date, String FMT) {
        long l = Long.parseLong(date);
        Date Datz = new Date(l * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat(FMT);
        return sdf.format(Datz);
    }

    /**
     * 获得当前日期时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getFormatDate1() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    /**
     * 获得当前日期时间
     *
     * @return yyyyMMddHHmmss
     */
    public static String getFormatDate2() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    /**
     * 将日期格式 yyyy-M-d 转化为yyyyMMdd
     *
     * @param date yyyy-M-d
     *
     * @return yyyyMMdd
     */
    public static String getFormatDate3(String date) {

        String year = date.split("-")[0];
        String month = date.split("-")[1];
        String day = date.split("-")[2];
        if (month.length() == 1) {
            month = "0" + month;
        }
        if (day.length() == 1) {
            day = "0" + day;
        }
        return year + month + day;

    }

    /**
     * 将日期格式 yyyyMMdd 转化为 yyyy-MM-dd
     *
     * @param date yyyyMMdd
     *
     * @return yyyy-MM-dd
     */
    public static String getFormatDate4(String date) {
        String year = date.substring(0, 4);
        String month = date.substring(4, 6);
        String day = date.substring(6, 8);
        return year + "-" + month + "-" + day;
    }

    /**
     * 将日期格式 YYYYMMDDHHMMSS 转化为 YYYY-MM-DD HH:MM:SS
     *
     * @param date YYYYMMDDHHMMSS
     *
     * @return YYYY-MM-DD HH:MM:SS
     */
    public static String getFormatDate5(String date) {
        String year = date.substring(0, 4);
        String month = date.substring(4, 6);
        String day = date.substring(6, 8);
        String hour = date.substring(8, 10);
        String min = date.substring(10, 12);
        String sec = date.substring(12, 14);
        return year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec;
    }

    /**
     * 获得当前时间
     *
     * @return HH:mm
     */
    public static String getFormatTime1() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    /**
     * 获得当前时间
     *
     * @return HH:mm:ss
     */
    public static String getFormatTime2() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    /**
     * 将时间格式 HH:mm 转化为 HHmm
     *
     * @return HHmm
     */
    public static String getFormatTime3(String time) {
        return time.replaceAll(":", "");
    }

    /**
     * 将时间格式 HHmm 转化为 HH:mm
     *
     * @param time HHmm
     *
     * @return HH:mm
     */
    public static String getFormatTime4(String time) {
        String hour = "00";
        String min = "00";
        if (time.length() == 4) {
            hour = time.substring(0, 2);
            min = time.substring(2, 4);
        }
        return hour + ":" + min;
    }

    /**
     * 根据指定的时间戳，返回指定格式的日期时间
     * <p/>
     * 指定的日期格式<br>
     * eg:<br>
     * "yyyy-MM-dd HH:mm:ss"<br>
     * "yyyy-MM-dd"<br>
     * "yyyyMMddHHmmss"<br>
     * "HH:mm:ss"<br>
     *
     * @return
     */
    public static String getFormatTime(long time, String format) {
        String strs = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            strs = sdf.format(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }

    /**
     * @param day 输入日期格式
     *
     * @return 获取指定日期往前或者往后几天的日期
     */
    public static String getFormatLeaveDay(String day, int leaveDay) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.DATE, leaveDay);
        date = calendar.getTime();
        return simpleDateFormat.format(date);
    }

    /**
     * @param day 输入日期格式 yyyy-MM-dd
     *
     * @return 获得前一天的日期
     */
    public static String getFormatBeforeDay(String day) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(day);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1); // 得到前一天
        date = calendar.getTime();
        return simpleDateFormat.format(date);
    }

    /**
     * @param day 输入日期格式 yyyy-MM-dd
     *
     * @return 获得后一天的日期
     */
    public static String getFormatNextDay(String day) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(day);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, +1); // 得到后一天
        date = calendar.getTime();
        return simpleDateFormat.format(date);
    }

    /**
     * 获得输入日期的星期
     *
     * @param inputDate 需要转换的日期 yyyy-MM-dd
     *
     * @return 星期×
     */
    public static String getWeekDay(String inputDate) {
        // String weekStrArr1[] = {"周日","周一","周二","周三","周四","周五","周六"};
        String weekStrArr1[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int outWeek = calendar.get(Calendar.DAY_OF_WEEK);// 返回的是1-7的整数，1为周日，2为周一，以此类推。
        return weekStrArr1[outWeek - 1];
    }

    /**
     * 检测时间是否在某个时间段内
     *
     * @param timeSlot 时间段 00：00--24：00
     * @param time     需要检测的时间 00：23
     *
     * @return
     */
    public static boolean isInsideTime(String timeSlot, String time) {
        String startTime = timeSlot.split("--")[0];
        String endTime = timeSlot.split("--")[1];
        boolean isGreaterStart = compareTime(time, startTime);
        boolean isLessEnd = compareTime(endTime, time);
        return isGreaterStart && isLessEnd;
    }

    /**
     * 比较两个时间的大小
     *
     * @param time1 00：23
     * @param time2 00：25
     *
     * @return time1大于等于time2 为 true,time1小于time2 为 false
     */
    public static boolean compareTime(String time1, String time2) {
        if (time1.equals("24:00") || time2.equals("00:00")) {
            return true;
        }
        if (time2.equals("24:00") || time1.equals("00:00")) {
            return false;
        }
        // DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat df = new SimpleDateFormat("HH:mm");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(time1));
            c2.setTime(df.parse(time2));
        } catch (ParseException e) {
            System.err.println("格式不正确");
        }
        int result = c1.compareTo(c2);

        if (result < 0) {
            return false;
        } else if (result >= 0) {
            return true;
        }
        return true;
    }

    /**
     * 比较两个日期的大小
     *
     * @param date1 2012-5-11
     * @param date2 2012-5-11
     *
     * @return date1大于等于date2 为 true,date1小于date2 为 false
     */
    public static int compareDate(String date1, String date2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(date1));
            c2.setTime(df.parse(date2));
        } catch (ParseException e) {
            System.err.println("格式不正确");
        }
        int result = c1.compareTo(c2);

        if (result < 0) {
            return -1;
        } else if (result >= 0) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 比较两个日期的大小
     *
     * @param date1 2012-5-11
     * @param date2 2012-5-11
     *
     * @return date1大于等于date2 为 true,date1小于date2 为 false
     */
    public static int compareDate(String date1, String date2, String format) {
        DateFormat df = new SimpleDateFormat(format);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(date1));
            c2.setTime(df.parse(date2));
        } catch (ParseException e) {
            System.err.println("格式不正确");
        }
        int result = c1.compareTo(c2);

        if (result < 0) {
            return -1;
        } else if (result >= 0) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 返回文字描述的日期
     *
     * @param date
     *
     * @return
     */
    public static String getTimeFormatText(Date date) {
        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - date.getTime();
        long r = 0;
        if (diff > year) {
            r = (diff / year);
            return r + "年前";
        }
        if (diff > month) {
            r = (diff / month);
            return r + "月前";
        }
        if (diff > day) {
            r = (diff / day);
            return r + "天前";
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }

    public static String getTimeFormatText2(String time) {
        if (time == null) {
            return null;
        }
        long b = System.currentTimeMillis();
        long diff = b - Long.valueOf(time + "000");
        long r = 0;
        if (diff > year) {
            r = (diff / year);
            return r + "年前";
        }
        if (diff > month) {
            r = (diff / month);
            return r + "月前";
        }
        if (diff > day) {
            r = (diff / day);
            return r + "天前";
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }

    public static String stringToDateSimple(String lo) {
        long time = Long.parseLong(lo);
        Date date = new Date(time * 1000);
        SimpleDateFormat sd = new SimpleDateFormat(DATE_FMT2);
        return sd.format(date);
    }

    public static String stringToDateSimple2(String lo, String format) {
        long time = Long.parseLong(lo);
        Date date = new Date(time * 1000);
        SimpleDateFormat sd = new SimpleDateFormat(format);
        return sd.format(date);
    }

    /**
     * 获取当前系统时间 格式为：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getSystemTime() {
        return getSystemTime(FMT);
    }

    /**
     * 获取当前系统时间
     *
     * @param format 时间格式化
     *
     * @return
     */
    public static String getSystemTime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    /**
     * 获取系统long格式类型的时间 格式为：yyyyMMddHHmmss
     *
     * @return
     */
    public static long getSystemTimeForLong() {
        return Long.parseLong(getSystemTime(L_FMT));
    }

    /**
     * 获取系统long格式类型的日期 格式为：yyyyMMdd
     *
     * @return
     */
    public static long getSystemDateForLong() {
        return Long.parseLong(getSystemTime(L_DATE_FMT));
    }

    /**
     * 将日期字符串转化为存储到数据库中的long格式类型
     *
     * @param date   日期字符串
     * @param format ex:yyyy-MM-dd HH:mm:ss格式
     *
     * @return
     */
    public static long getDateForLong(String date, String format) {
        return getDateForLong(date, format, L_FMT);
    }

    /**
     * 将日期字符串转化为存储到数据库中的long格式类型
     *
     * @param date        日期字符串
     * @param format1---- yyyy-MM-dd HH:mm:ss格式
     * @param format2---- yyyyMMddHHmmssSSS格式
     *
     * @return
     */
    public static long getDateForLong(String date, String format1, String format2) {
        SimpleDateFormat sdf1 = new SimpleDateFormat(format1);
        SimpleDateFormat sdf2 = new SimpleDateFormat(format2);
        long parseLong = 0;
        try {
            Date parse = sdf1.parse(date);
            String format = sdf2.format(parse);
            parseLong = Long.parseLong(format);
        } catch (ParseException e) {

        }
        return parseLong;
    }

    /**
     * 将日期字符串转化为存储到数据库中的long格式类型
     *
     * @param date
     *
     * @return
     */
    public static long getDateForLong(String date) {
        return getDateForLong(date, FMT, L_FMT);
    }

    /**
     * 将区间日期字符串格式化
     * ex：2014-01-01 To 2015-01-01
     * 返回 两个long
     *
     * @param date
     *
     * @return
     */
    public static long[] getRangDateForLong(String date) {
        if (date == null || "".equals(date))
            return new long[]{0, 0};
        if (date.indexOf("To") == -1) {
            return new long[]{0, 0};
        }
        String temp = date.replaceAll(" ", "");
        String[] split = temp.split("To");
        return new long[]{getDateForLong(split[0], DATE_FMT, L_FMT),
                getDateForLong(split[1], DATE_FMT, L_FMT)};
    }

    public static long getDateForLong(Date date, String format2) {
        SimpleDateFormat sdf2 = new SimpleDateFormat(format2);
        long parseLong = 0;
        String format = sdf2.format(date);
        parseLong = Long.parseLong(format);
        return parseLong;
    }

    /**
     * 将yyyyMMddHHmmssSSS结构的日期转换为yyyy-MM-dd HH:mm:ss
     *
     * @param date
     *
     * @return
     */
    public static String format(String date) {
        return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8) + " "
                + date.substring(8, 10) + ":" + date.substring(10, 12) + ":"
                + date.substring(10, 12);
    }

    /**
     * 获取当年的第一天
     *
     * @return
     */
    public static Date getCurrYearFirst() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     *
     * @return Date
     */
    public static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取当年的最后一天
     *
     * @return
     */
    public static Date getCurrYearLast() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     *
     * @return Date
     */
    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        return currYearLast;
    }

    /**
     * 格式化时间
     *
     * @param date
     * @param format
     *
     * @return
     */
    public static String format(Date date, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(date);
    }

    /**
     * 以天为单位，获得date后period天的long型0点日期值
     *
     * @param date
     * @param period
     *
     * @return
     */
    public static long getDateAfterPeriod(long date, int period) {
        Date startDate = getDateByLong(Long.parseLong(date / 1000000000 + "000000000"));
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, period);//以天为单位
        startDate = calendar.getTime();
        long endDate = getDateForLong(startDate);
        return endDate;
    }

    /**
     * 将日期转换为long类型
     *
     * @param date
     *
     * @return
     */
    public static long getDateForLong(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(L_FMT);
        return Long.parseLong(sdf.format(date));
    }

    /**
     * 将long类型的日期转化为中文日期格式
     *
     * @param date
     *
     * @return
     */
    public static String getDateStringForLong(long date) {
        Date Datz = getDateByLong(date);
        SimpleDateFormat sdf = new SimpleDateFormat(C_FMT);
        return sdf.format(Datz);
    }

    /**
     * 计算差多少天
     *
     * @param date1
     * @param date2
     *
     * @return
     */
    public static long getDif(long date1, long date2) {
        Date sDate = getDateByLong(date1);
        Date eDate = getDateByLong(date2);
        long bet = sDate.getTime() - eDate.getTime();
        return bet / (24 * 60 * 60 * 1000);
    }

    /**
     * 将yyyy-MM-dd格式的数据转换为int类型数据
     *
     * @param date
     *
     * @return
     * @throws ParseException
     */
    public static int getTimeForInt(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FMT);
        int result = 0;
        try {
            Date time = sdf.parse(date);
            long longTime = getDateForLong(time);
            result = (int) (longTime / 1000000000);
        } catch (ParseException e) {
        }
        return result;
    }

    /**
     * 通过开始日期和期限计算出每个自然月与开始日期同天的日期
     *
     * @param start
     * @param period
     *
     * @return
     */
    public static List<Date> caculateMonthDate(Date start, int period) {
        List<Date> repayDays = new ArrayList<Date>();
        Calendar cal = Calendar.getInstance();
        if (start == null) {
            return repayDays;
        }
        cal.setTime(start);
        Calendar calLast = Calendar.getInstance();
        calLast.setTime(start);
        calLast.add(Calendar.DATE, period);
        //若天小于31天则取下一个月的那天，但如果为2月则取下个月
        if (cal.get(Calendar.DAY_OF_MONTH) < 31) {
            int day = cal.get(Calendar.DAY_OF_MONTH);
            while (cal.getTime().getTime() < calLast.getTime().getTime()) {
                cal.add(Calendar.MONTH, 1);
                int month = cal.get(Calendar.MONTH);
                if (month != 1) {
                    cal.set(Calendar.DAY_OF_MONTH, day);
                }
                if (cal.getTime().getTime() > calLast.getTime().getTime()) {
                    repayDays.add(calLast.getTime());
                } else {
                    repayDays.add(cal.getTime());
                }
            }
            //若为31天则取每个月的最大值
        } else {
            while (cal.getTime().getTime() < calLast.getTime().getTime()) {
                cal.add(Calendar.DATE, 1);
                cal.set(Calendar.DAY_OF_MONTH, 1);
                cal.roll(Calendar.DAY_OF_MONTH, -1);
                if (cal.getTime().getTime() > calLast.getTime().getTime()) {
                    repayDays.add(calLast.getTime());
                } else {
                    repayDays.add(cal.getTime());
                }
            }
        }
        return repayDays;
    }

    public String get_instance_from_now(Date date) {
        long a1 = System.currentTimeMillis() - date.getTime();
        double hours = (double) (a1) / 3600 / 1000;

        return hours + "小时";

    }
}
