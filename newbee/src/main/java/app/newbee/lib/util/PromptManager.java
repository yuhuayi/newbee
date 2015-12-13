package app.newbee.lib.util;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.workingchat.newbee.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 提示信息的管理
 */

public class PromptManager {

    private static Toast mToast;


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

    /**
     * @param context
     * @param msg
     * @return
     */
    public static Dialog showLoadDataDialog(Context context, String msg) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_circle_dialog, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        if (null != msg) {
            tipTextView.setText(msg);// 设置加载信息
        }
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);

        loadingDialog.setCancelable(true);// 不可以用“返回键”取消
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        return loadingDialog;
    }


    public static AlertDialog showCustomDialog(Context context, String title, String message, String leftText, String rightText,
                                               View.OnClickListener leftOnClickListener, View.OnClickListener rightOnClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.dialog_layout, null);
        TextView dtitle = (TextView) view.findViewById(R.id.dialog_title);
        TextView dmessage = (TextView) view.findViewById(R.id.dialog_message);
        TextView dcancel = (TextView) view.findViewById(R.id.dialog_cancel);
        TextView dcommit = (TextView) view.findViewById(R.id.dialog_commit);

        dtitle.setText(title);
        dmessage.setText(message);
        dcancel.setText(leftText);
        dcommit.setText(rightText);

        dcancel.setOnClickListener(leftOnClickListener);
        dcommit.setOnClickListener(rightOnClickListener);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(view, 0, 0, 0, 0);
        return dialog;
    }


    public static AlertDialog showSimpleDialog(Context context, String title, String message, String btText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.simple_dialog_layout, null);
        TextView content = (TextView) view.findViewById(R.id.content);
        TextView dialog_title = (TextView) view.findViewById(R.id.dialog_title);
        TextView dcommit = (TextView) view.findViewById(R.id.dialog_commit);

        content.setText(message);
        dialog_title.setText(title);
        dcommit.setText(btText);

        final AlertDialog simpleDialog = builder.create();
        simpleDialog.setView(view, 0, 0, 0, 0);
        simpleDialog.setCanceledOnTouchOutside(false);
        dcommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleDialog.dismiss();
            }
        });
        simpleDialog.show();
        return simpleDialog;
    }

    public static Pair<AlertDialog, EditText> showInputDialog(Context context, String title, String hint, String left, String right, View.OnClickListener leftOnClickListener, View.OnClickListener rightOnClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.dialog_layout_in_put, null);
        TextView dtitle = (TextView) view.findViewById(R.id.dialog_title);
        final EditText editText_group_name = (EditText) view.findViewById(R.id.dialog_message);
        TextView dcancel = (TextView) view.findViewById(R.id.dialog_cancel);
        TextView dcommit = (TextView) view.findViewById(R.id.dialog_commit);

        dtitle.setText(title);
        editText_group_name.setHint(hint);
        dcancel.setText(left);
        dcommit.setText(right);

        AlertDialog dialog_group_name_ = builder.create();
        dialog_group_name_.setCanceledOnTouchOutside(false);
        dialog_group_name_.setView(view, 0, 0, 0, 0);

        dcancel.setOnClickListener(leftOnClickListener);

        dcommit.setOnClickListener(rightOnClickListener);


        editText_group_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String editable = editText_group_name.getText().toString();
                String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5()_-]";//只允许字母、数字和汉字()_-
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(editable);

                String str = m.replaceAll("").trim();
                if (!editable.equals(str)) {
                    editText_group_name.setText(str);
                    editText_group_name.setSelection(str.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        Pair<AlertDialog, EditText> alertDialogEditTextPair = new Pair<>(dialog_group_name_, editText_group_name);
        return alertDialogEditTextPair;
    }

    public static AlertDialog showSimpleCustomDialog(Context context, String title, String message, String btText,
                                                     View.OnClickListener btOnClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.simple_dialog_layout, null);
        TextView content = (TextView) view.findViewById(R.id.content);
        TextView dialog_title = (TextView) view.findViewById(R.id.dialog_title);
        TextView dcommit = (TextView) view.findViewById(R.id.dialog_commit);

        content.setText(message);
        dialog_title.setText(title);
        dcommit.setText(btText);

        AlertDialog simpleDialog = builder.create();
        simpleDialog.setView(view, 0, 0, 0, 0);
        simpleDialog.setCanceledOnTouchOutside(false);
        dcommit.setOnClickListener(btOnClickListener);
//		simpleDialog.show();
        return simpleDialog;
    }


}
