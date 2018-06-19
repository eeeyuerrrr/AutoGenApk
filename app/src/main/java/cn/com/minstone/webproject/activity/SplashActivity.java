package cn.com.minstone.webproject.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import cn.com.minstone.webproject.R;
import cn.com.minstone.webproject.databinding.ActivitySplashBinding;

/***
 * 项目名称：ZJMarketSubject <br>
 * 描述：启动图界面
 * 最近修改时间：2017/4/24 14:29
 * @since 2017/4/24
 * @author yujq
 */
public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_STAY_TIME = 2000; //停留时间


    private ActivitySplashBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toBrowerActivity();
        SplashActivity.this.finish();

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

//        initSplashImg();

//        delayToHomeActivity();
    }

    private void initSplashImg(){
        Glide.with(this).load(R.drawable.splash_img).into(mBinding.spashImg);
    }

    private void toBrowerActivity(){
        String url = getResources().getString(R.string.web_home_url);
        BrowserActivity.startActivity(SplashActivity.this,url);
    }

    private void delayToHomeActivity(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(SPLASH_STAY_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                HomeActivity.start(SplashActivity.this);

                String url = getResources().getString(R.string.web_home_url);
                 BrowserActivity.startActivity(SplashActivity.this,url);
                SplashActivity.this.finish();
            }
        }).start();
    }

}
