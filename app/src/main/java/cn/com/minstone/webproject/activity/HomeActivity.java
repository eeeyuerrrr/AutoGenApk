package cn.com.minstone.webproject.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import cn.com.minstone.webproject.R;
import cn.com.minstone.webproject.databinding.ActivityHomeBinding;
import cn.com.minstone.webproject.view.X5WebView;

/***
 * 项目名称：ZJMarketSubject <br>
 * 描述：首页
 * 最近修改时间：2017/4/24 14:29
 * @since 2017/4/24
 * @author yujq
 */
public class HomeActivity extends Activity {

    private ActivityHomeBinding mBinding;

    private X5WebView mWebView;
    private ProgressBar mProgressBar;
    private RelativeLayout mErrorView;

    private String mOrignalUrl; //加载出错时保存出错前的url

    private Handler mHandler = new Handler();

    public static void start(Context from) {
        Intent intent = new Intent(from, HomeActivity.class);
        from.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        initView();

        String url = getResources().getString(R.string.web_home_url);
        loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if (mWebView != null && mWebView.canGoBack()) {
            if (mErrorView.getVisibility() != View.GONE) {
                mErrorView.setVisibility(View.GONE);
            }
            mWebView.goBack();
        } else {
            popDialogConfirmExit();
        }
    }


    private void loadUrl(String url) {
        if (mErrorView.getVisibility() != View.GONE) {
            mErrorView.setVisibility(View.GONE);
        }
        mWebView.loadUrl(url);
    }

    private void popDialogConfirmExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定要退出应用？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HomeActivity.this.finish();
                    }
                })
                .create()
                .show();
    }

    private void initView() {
        mProgressBar = mBinding.progressBar;
        mProgressBar.setVisibility(View.GONE);
        mWebView = mBinding.webView;
        mErrorView = mBinding.errorView;

//        initWebSetting();

        initWebViewClient();

        initWebViewChromeClient();

        mErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebView != null) {
                    loadUrl(mOrignalUrl);
                }
            }
        });
    }

//    private void initWebSetting() {
//        WebSettings webSetting = mWebView.getSettings();
//        webSetting.setAllowFileAccess(true);
//        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        webSetting.setSupportZoom(false);
//        webSetting.setBuiltInZoomControls(false);
//        webSetting.setUseWideViewPort(true);
//        webSetting.setSupportMultipleWindows(false);
//        webSetting.setLoadWithOverviewMode(true);
//        webSetting.setAppCacheEnabled(true);
//        webSetting.setDatabaseEnabled(true);
//        webSetting.setDomStorageEnabled(true);
//        webSetting.setJavaScriptEnabled(true);
//        webSetting.setGeolocationEnabled(true);
//        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
//        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
//        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
//        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
//                .getPath());
//        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
//        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
//        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
//    }

    private void initWebViewClient() {

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                // 接受所有网站的证书
                sslErrorHandler.proceed();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e("webapp","err happend >>> " + description);
                mOrignalUrl = failingUrl;
                mErrorView.setVisibility(View.VISIBLE);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });

    }


    private void initWebViewChromeClient() {

        mWebView.setWebChromeClient(new WebChromeClient() {

//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                if (newProgress == 100) {
//                    mProgressBar.setVisibility(View.GONE);
//                } else {
//                    mProgressBar.setVisibility(View.VISIBLE);
//                    mProgressBar.setProgress(newProgress);
//                }
//                super.onProgressChanged(view, newProgress);
//            }

        });

    }


}
