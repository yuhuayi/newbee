package app.newbee.lib.util;

import java.math.BigDecimal;

public class ArithUtils {

    //默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;

    //这个类不能实例化
    private ArithUtils() {

    }

    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }
    public static double add(double v1, double v2,int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    public static String add(String v1, String v2) {
        if (StringUtils.isEmpty(v1)) {
            if (StringUtils.isEmpty(v2)){
                return "0.00";
            }else{
                return v2;
            }
        }
        if (StringUtils.isEmpty(v2)) {
            return v1;
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).toString();
    }

    public static BigDecimal add(String v1, String v2, int scale) {
        if (StringUtils.isEmpty(v1)) {
            if (StringUtils.isEmpty(v2)){
                return new BigDecimal("0.00");
            }else{
                return  new BigDecimal(v2);
            }
        }
        if (StringUtils.isEmpty(v2)) {
            return new BigDecimal(v1);
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static double sub(double v1, double v2) {

        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    public static String sub(String v1, String v2) {
        if (StringUtils.isEmpty(v1)) {
            return "0.00";
        }
        if (StringUtils.isEmpty(v2)) {
            return v1;
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).toString();
    }

    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    //    public static double mul(String v1, String v2) {
//        BigDecimal b1 = new BigDecimal(v1);
//        BigDecimal b2 = new BigDecimal(v2);
//        return b1.multiply(b2).doubleValue();
//    }
    public static String mul(String v1, String v2) {
        if (StringUtils.isEmpty(v1)) {
            if (StringUtils.isEmpty(v2)){
                return "0.00";
            }else{
                return  v2;
            }
        }
        if (StringUtils.isEmpty(v2)) {
            return  v1;
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).toString();
    }

    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double div(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        if (StringUtils.isEmpty(v1)) {
            if (StringUtils.isEmpty(v2)){
                return 0.00;
            }else{
                return  new BigDecimal(v2).doubleValue();
            }
        }
        if (StringUtils.isEmpty(v2)){
            return  new BigDecimal(v1).doubleValue();
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static float convertsToFloat(double v) {
        BigDecimal b = new BigDecimal(v);
        return b.floatValue();
    }

    public static int convertsToInt(double v) {
        BigDecimal b = new BigDecimal(v);
        return b.intValue();
    }

    public static long convertsToLong(double v) {
        BigDecimal b = new BigDecimal(v);
        return b.longValue();
    }

    public static double returnMax(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.max(b2).doubleValue();
    }

    public static double returnMin(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.min(b2).doubleValue();
    }

    public static int compareTo(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.compareTo(b2);
    }

    public static int compareTo(String v1, String v2) {
        if (StringUtils.isEmpty(v1)) {
            if (StringUtils.isEmpty(v2)){
                return 0;
            }else{
                return -1;
            }
        }
        if (StringUtils.isEmpty(v2)){
            return 1;
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.compareTo(b2);
    }
}
