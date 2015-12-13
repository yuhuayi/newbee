package app.newbee.lib;

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
