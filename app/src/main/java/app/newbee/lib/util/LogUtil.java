package app.newbee.lib.util;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * 日志记录
 * 另外还有一个是L.java的log工具, 试试好用不
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
        if (Tools.hasSdcard()) {
            if (Tools.getSDFreeSize() > 1) {
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
                Log.i(clazz.getSimpleName(), msg);
                break;
            case DEBUG:
                // 在应用下面创建目录存放日志,启动上传

                break;
            case BATE:
                // write to sdcard
                String time = TimeUtils.getFormatDate(pattern);
                if (Tools.hasSdcard()) {
                    if (outputStream != null && Tools.getSDFreeSize() > 1) {
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
                        Log.i("SDCAEDTAG", "file is null or storage insufficient");
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
                Log.i(tag, msg);
                break;
            case DEBUG:
                // 在应用下面创建目录存放日志,启动上传

                break;
            case BATE:
                // write to sdcard
                String time = TimeUtils.getFormatDate(pattern);
                if (Tools.hasSdcard()) {
                    if (outputStream != null && Tools.getSDFreeSize() > 1) {
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
                        Log.i("SDCAEDTAG", "file is null or storage insufficient");
                    }
                }
                break;
            case RELEASE:
                // 一般不做日志记录
                break;
        }
    }
}
