package app.newbee.lib.util;

import java.math.BigDecimal;

public class TransformTools {
    public static String secondTransToformatString(int duration) {
        // 秒
        long sec = duration;
        // 分钟
        long min = duration  % 60;
        // 小时
        long hour = (duration / 60 / 60) % 24;
        // 天
        long day = duration / 60 / 60 / 24;

        if (day > 0) {
            return day + "天" + hour + "小时" + min + "分钟" ;
        }

        if (hour > 0) {
            return hour + "小时" + min + "分钟";
        }

        if (min > 0) {
            return min + "分钟" ;
        }

        return sec + "秒";
    }

    public static String meterTransToformatString(int distance) {
        // 米
        int ssec = distance;
        // 千米
        int sec = distance / 1000;
        if (distance < 1000) {
            return sec + "米";
        } else {
            BigDecimal b = new BigDecimal(distance / 1000);
            double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            return f1 + "千米";
        }
    }
}
