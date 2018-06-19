package cn.com.minstone.webproject.app;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsDownloader;

/***
 * 项目名称：android <br>
 * 描述：Application类
 * @since 2017/7/6
 * @author yujq
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initX5WebView();
    }

    private void initX5WebView(){
        QbSdk.setDownloadWithoutWifi(true);
        TbsDownloader.needDownload(getApplicationContext(),true);
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("x5webview","x5webview onViewInitFinished ,is use x5webview: " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);

    }

}
