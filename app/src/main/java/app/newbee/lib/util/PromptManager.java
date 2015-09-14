package app.newbee.lib.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.newbee.lib.R;

/**
 * 提示信息的管理
 */

public class PromptManager {

    // 当测试阶段时true
    private static final boolean isShow = true;
    private static Toast mToast;

    /**
     * @param context
     * @param msg
     * @方法名称:showToastTest
     * @描述: 测试用 在正式投入市场：删
     * @创建人：曹睿翔
     * @创建时间：2014年9月3日 上午10:31:26
     * @备注：
     * @返回类型：void
     */
    public static void showToastTest(Context context, String msg) {
        if (isShow) {
            showCustomToast(context, msg);
        }
    }

    public static void showCustomToast(Context context, CharSequence text) {
        if (mToast == null) {
            mToast = new Toast(context);
            // 获取LayoutInflater对象
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // 由layout文件创建一个View对象
            View layout = inflater.inflate(R.layout.toast_layout, null);
            mToast.setView(layout);
            mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        }
        mToast.setDuration(Toast.LENGTH_SHORT);
        TextView textView = (TextView) mToast.getView().findViewById(R.id.text0);
        textView.setText(text);
        mToast.show();
    }


}
