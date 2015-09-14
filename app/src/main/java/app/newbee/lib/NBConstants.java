package app.newbee.lib;

import android.net.Uri;

public class NBConstants {
    public static final String RESPONSE_CACHE = "RESPONSE_CACHE";
    public static final long RESPONSE_CACHE_SIZE = 1024*104*12;
    public static final long HTTP_CONNECT_TIMEOUT = 10 * 1000;
    public static final long HTTP_READ_TIMEOUT = 10 * 1000;
    // 文件路径信息
    public static String LOG_PATH = "twotiger_log.txt";
    /**
     * false  情况:fragment 跳转用 replace
     * true   情况: fragment 跳转用 add ,show
     */
    public static final String CTWAP = "ctwap";
    public static final String CMWAP = "cmwap";
    public static final String WAP_3G = "3gwap";
    public static final String UNIWAP = "uniwap";
    public static final int TYPE_CM_CU_WAP = 4;// 移动联通wap10.0.0.172
    public static final int TYPE_CT_WAP = 5;// 电信wap 10.0.0.200
    public static final int TYPE_OTHER_NET = 6;// 电信,移动,联通,wifi 等net网络
    public static Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");
    public static String DOWNLOAD_APP_NAME = "twotiger.apk";
    public static String CacheName = "imgCache";
    public static final int INITIAL_DELAY_MILLIS = 300;
    // 数据库版本
    public static int db_version = 1;
    public static int current_page_take_pic;
    public static final int photo_code = 101;
    public static final int carmer_code = 102;
    public static final int corp_img_code = 103;
    public static final int Schedule_code = 104;
    public static final int shoppingCar_item_check = 105;
    public static String FILE_SCHEME = "file:///";
    public static final int PromptRequestCode = 1001;

}
