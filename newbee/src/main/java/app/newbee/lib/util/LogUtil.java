package app.newbee.lib.util;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * 日志记录
 */
public class LogUtil {
    /**
     * 开发阶段
     */
    private static final int DEVELOP = 0;
    /**
     * 内部测试阶段
     */
    private static final int DEBUG = 1;
    /**
     * 公开测试
     */
    private static final int BATE = 2;
    /**
     * 正式版
     */
    private static final int RELEASE = 3;

    /**
     * 当前阶段标示
     */
    private static int currentStage = DEVELOP;

    private static File file;
    private static FileOutputStream outputStream;
    private static String pattern = "yyyy-MM-dd HH:mm:ss";

    static {
        if (AppUtils.hasSdcard()) {
            if (AppUtils.getSDFreeSize() > 1) {
//				file = FileUtils.getDiskCacheDir(null, Constants.LOG_PATH);
//				try {
//					outputStream = new FileOutputStream(file, true);
//				} catch (FileNotFoundException e) {
//					// can't happen
//					e.printStackTrace();
//				}
            } else {
                // storage space is insufficient
            }
        } else {
            // SDcard isn't Exist
        }
    }

    public static void info(String msg) {
        info(LogUtil.class, msg);
    }

    public static void info(@SuppressWarnings("rawtypes") Class clazz, String msg) {

        switch (currentStage) {
            case DEVELOP:
                // output to the console
                i(clazz.getSimpleName(), msg);
                break;
            case DEBUG:
                // 在应用下面创建目录存放日志,启动上传

                break;
            case BATE:
                // write to sdcard
                String time = TimeUtils.getFormatDate(pattern);
                if (AppUtils.hasSdcard()) {
                    if (outputStream != null && AppUtils.getSDFreeSize() > 1) {
                        try {
                            outputStream.write(time.getBytes());
                            String className = "";
                            if (clazz != null) {
                                className = clazz.getSimpleName();
                            }
                            outputStream.write(("    " + className + "\r\n").getBytes());

                            outputStream.write(msg.getBytes());
                            outputStream.write("\r\n".getBytes());
                            outputStream.flush();
                        } catch (IOException e) {

                        }
                    } else {
                        i("SDCAEDTAG", "file is null or storage insufficient");
                    }
                }
                break;
            case RELEASE:
                // 一般不做日志记录
                break;
        }
    }

    public static void info(String tag, String msg) {

        switch (currentStage) {
            case DEVELOP:
                // output to the console
                i(tag, msg);
                break;
            case DEBUG:
                // 在应用下面创建目录存放日志,启动上传

                break;
            case BATE:
                // write to sdcard
                String time = TimeUtils.getFormatDate(pattern);
                if (AppUtils.hasSdcard()) {
                    if (outputStream != null && AppUtils.getSDFreeSize() > 1) {
                        try {
                            outputStream.write(time.getBytes());
                            String className = "";

                            outputStream.write(("    " + tag + "\r\n").getBytes());

                            outputStream.write(msg.getBytes());
                            outputStream.write("\r\n".getBytes());
                            outputStream.flush();
                        } catch (IOException e) {

                        }
                    } else {
                        i("SDCAEDTAG", "file is null or storage insufficient");
                    }
                }
                break;
            case RELEASE:
                // 一般不做日志记录
                break;
        }
    }

    /**
     * This flag to indicate the log is enabled or disabled.
     */
    private static boolean isLogEnable = true;

    /**
     * Disable the log output.
     */
    public static void disableLog() {
        isLogEnable = false;
    }

    /**
     * Enable the log output.
     */
    public static void enableLog() {
        isLogEnable = true;
    }

    /**
     * Debug
     *
     * @param tag
     * @param msg
     */
    public static void d(String tag, String msg) {
        if (isLogEnable) {
            StackTraceElement stackTraceElement = java.lang.Thread.currentThread().getStackTrace()[3];
            Log.d(tag, rebuildMsg(stackTraceElement, msg));
        }
    }

    /**
     * Information
     *
     * @param tag
     * @param msg
     */
    public static void i(String tag, String msg) {
        if (isLogEnable) {
            StackTraceElement stackTraceElement = java.lang.Thread.currentThread().getStackTrace()[3];
            Log.i(tag, rebuildMsg(stackTraceElement, msg));
        }
    }

    /**
     * Verbose
     *
     * @param tag
     * @param msg
     */
    public static void v(String tag, String msg) {
        if (isLogEnable) {
            StackTraceElement stackTraceElement = java.lang.Thread.currentThread().getStackTrace()[3];
            Log.v(tag, rebuildMsg(stackTraceElement, msg));
        }
    }

    /**
     * Warning
     *
     * @param tag
     * @param msg
     */
    public static void w(String tag, String msg) {
        if (isLogEnable) {
            StackTraceElement stackTraceElement = java.lang.Thread.currentThread().getStackTrace()[3];
            Log.w(tag, rebuildMsg(stackTraceElement, msg));
        }
    }

    /**
     * Error
     *
     * @param tag
     * @param msg
     */
    public static void e(String tag, String msg) {
        if (isLogEnable) {
            StackTraceElement stackTraceElement = java.lang.Thread.currentThread().getStackTrace()[3];
            Log.e(tag, rebuildMsg(stackTraceElement, msg));
        }
    }

    /**
     * Rebuild Log Msg
     *
     * @param msg
     * @return
     */
    private static String rebuildMsg(StackTraceElement stackTraceElement, String msg) {
        StringBuffer sb = new StringBuffer();
        sb.append(stackTraceElement.getFileName());
        sb.append(" (");
        sb.append(stackTraceElement.getLineNumber());
        sb.append(") ");
        sb.append(stackTraceElement.getMethodName());
        sb.append(": ");
        sb.append(msg);
        return sb.toString();
    }

}
