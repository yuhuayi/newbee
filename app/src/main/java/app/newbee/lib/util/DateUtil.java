package app.newbee.lib.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 
 * @Description: TODO(日期工具) 
 * @Author hansc
 * @Date 2014年8月8日 下午11:00:33
 */
public class DateUtil {

//    private static final Logger log = Logger.getLogger(DateUtil.class);
    //缺省日期格式化
    public static final String FMT = "yyyy-MM-dd HH:mm:ss";
    public static final String _FMT = "yyyy-MM-dd HH:mm";
    public static final String YM = "yyyy-MM";
    public static final String C_FMT = "yyyy年MM月dd日HH时mm分ss秒";
    public static final String L_FMT = "yyyyMMddHHmmssSSS";
    public static final String L_DATE_FMT = "yyyyMMdd";
    public static final String DATE_FMT = "yyyy-MM-dd";

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
     * @return
     */
    public static String getSystemTime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    /**
     * 获取系统long格式类型的时间 格式为：yyyyMMddHHmmss
     * @return
     */
    public static long getSystemTimeForLong() {
        return Long.parseLong(getSystemTime(L_FMT));
    }

    /**
     * 获取系统long格式类型的日期 格式为：yyyyMMdd
     * @return
     */
    public static long getSystemDateForLong() {
        return Long.parseLong(getSystemTime(L_DATE_FMT));
    }

    /**
     * 将日期转换为long类型
     * 
     * @param date
     * @return
     */
    public static long getDateForLong(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(L_FMT);
        return Long.parseLong(sdf.format(date));
    }

    /**
     * 将日期字符串转化为存储到数据库中的long格式类型
     * @param date 日期字符串
     * @param format1---- yyyy-MM-dd HH:mm:ss格式 
     * @param format2---- yyyyMMddHHmmssSSS格式
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
     * @param date 日期字符串
     * @param format ex:yyyy-MM-dd HH:mm:ss格式 
     * @return
     */
    public static long getDateForLong(String date, String format) {
        return getDateForLong(date, format, L_FMT);
    }

    /**
     * 将日期字符串转化为存储到数据库中的long格式类型
     * 
     * @param date
     * @return
     */
    public static long getDateForLong(String date) {
        return getDateForLong(date, FMT, L_FMT);
    }

    /**
     * 将区间日期字符串格式化
     * ex：2014-01-01 To 2015-01-01
     * 返回 两个long
     * @param date
     * @return 
     */
    public static long[] getRangDateForLong(String date) {
        if (date == null || "".equals(date))
            return new long[] { 0, 0 };
        if (date.indexOf("To") == -1) {
            return new long[] { 0, 0 };
        }
        String temp = date.replaceAll(" ", "");
        String[] split = temp.split("To");
        return new long[] { getDateForLong(split[0], DATE_FMT, L_FMT),
                           getDateForLong(split[1], DATE_FMT, L_FMT) };
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
     * @return
     */
    public static String format(String date) {
        return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8) + " "
               + date.substring(8, 10) + ":" + date.substring(10, 12) + ":"
               + date.substring(10, 12);
    }

    /** 
     * 获取当年的第一天 
     * @return
     */
    public static Date getCurrYearFirst() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    /** 
     * 获取当年的最后一天 
     * @return
     */
    public static Date getCurrYearLast() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }

    /** 
     * 获取某年第一天日期 
     * @param year 年份 
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
     * 获取某年最后一天日期 
     * @param year 年份 
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
     * @param date
     * @param format
     * @return
     */
    public static String format(Date date, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(date);
    }

    /**
     * 将数据库中的Long类型时间转换为date
     * 
     * @param date
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
     * 以天为单位，获得date后period天的long型0点日期值
     * @param date
     * @param period
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
     * 将long类型的日期转化为中文日期格式
     * @param date
     * @return
     */
    public static String getDateStringForLong(long date) {
        Date Datz = getDateByLong(date);
        SimpleDateFormat sdf = new SimpleDateFormat(C_FMT);
        return sdf.format(Datz);
    }
    /**
     * 将long类型的日期转化为需要日期格式
     * @param date
     * @return
     */
    public static String getDateStringForLong(long date,String FMT) {
        Date Datz = getDateByLong(date);
        SimpleDateFormat sdf = new SimpleDateFormat(FMT);
        return sdf.format(Datz);
    }
    /**
     * 计算差多少天
     * 
     * @param date1
     * @param date2
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
     * @param date
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
     * @return
     */
    public static List<Date> caculateMonthDate(Date start, int period) {
        List<Date> repayDays = new ArrayList<Date>();
        Calendar cal = Calendar.getInstance();
        if(start==null){
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
    
    public String get_instance_from_now(Date date){
    	long a1=System.currentTimeMillis()-date.getTime();
    	double hours = (double)(a1)/3600/1000;
    	
		return hours+"小时";
    	
    }
}
