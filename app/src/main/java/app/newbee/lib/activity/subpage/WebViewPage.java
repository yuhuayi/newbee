package app.newbee.lib.activity.subpage;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import app.newbee.lib.activity.base.BaseFragment;
import com.newbee.lib.R;

public class WebViewPage extends BaseFragment {

    public static final String SEARCH_HISTORY = "WebViewPage";
    private WebView mWebView;
    private String type;
    private boolean main;

    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup container) {
        View contactsLayout = inflater.inflate(R.layout.page_web_view, container, false);
        return contactsLayout;
    }

//    @Override
//    protected void findViewById(View view) {
//        mWebView = (WebView) view.findViewById(R.id.webview);
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            default:
//                break;
//        }
//    }
//
//    @Override
//    protected void setListener() {
//    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void processLogic() {
        try {
            if (mWebView != null) {
                mWebView.setWebViewClient(new NewsClient());
                String url = getArguments().getString("url");
                String titleText = getArguments().getString("title");
                type = getArguments().getString("type");

                if (TextUtils.isEmpty(url)) {
                    url = "http://www.twotiger.com";
                }
                mWebView.loadUrl(url);
                WebSettings mWebSettings = mWebView.getSettings();
                mWebSettings.setJavaScriptEnabled(true);
                mWebSettings.setAppCacheEnabled(true);
                mWebSettings.setAppCacheMaxSize(8 * 1024 * 1024);
                mWebSettings.setAllowFileAccess(true);
                mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
                mWebSettings.setDisplayZoomControls(true);
                mWebSettings.setBuiltInZoomControls(true);
                mWebSettings.setUseWideViewPort(true);
                mWebSettings.setSupportZoom(true);
                mWebView.setWebChromeClient(new MyWebChromeClient());
                mWebView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                            case MotionEvent.ACTION_UP:
                                if (!v.hasFocus()) {
                                    v.requestFocus();
                                }
                                break;
                        }
                        return false;
                    }
                });
            } else {

            }
            main = getArguments().getBoolean("main");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class NewsClient extends WebViewClient {

        private AlertDialog alertDialog;

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, final String url) {

            return true;
        }
    }

    private void htmlGoBack() {
        try {
            String currentGoBackUrl = mWebView.getUrl();
            if (mWebView != null) {
                if (currentGoBackUrl != null) {
                    popBackStack();
                } else {
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyWebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
