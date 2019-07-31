package com.grechur.wanandroid.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.grechur.wanandroid.R;
import com.grechur.wanandroid.utils.UserInfoTools;
import com.grechur.wanandroid.utils.imageload.ImageLoader;
import com.grechur.wanandroid.utils.imageload.ImageLoaderUtil;
import com.grechur.wanandroid.utils.imageload.hasprogress.ProgressListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AboutUsActivity extends AppCompatActivity {
    @BindView(R.id.sw_day_night)
    Switch sw_day_night;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.iv_img)
    ImageView mImageView;
    @BindView(R.id.tv_progress)
    TextView tv_progress;

    Unbinder unbinder;

    String url = "http://abc.2008php.com/2015_Website_appreciate/2015-09-20/20150920191026.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        unbinder = ButterKnife.bind(this);

        title.setText("关于我们");

        Drawable drawable = getResources().getDrawable(R.mipmap.arrow_left);
        iv_back.setImageDrawable(drawable);

        if(UserInfoTools.isNightMode(this)){
            sw_day_night.setChecked(true);
        }else{
            sw_day_night.setChecked(false);
        }

        sw_day_night.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
                    setNightMode();
//                }
            }
        });

        ProgressListener progressListener = new ProgressListener() {
            @Override
            public void onProgress(long currentByte, long countByte) {
                int d = (int) (100*currentByte/countByte);
                tv_progress.setText(d+"");
            }
        };
        ImageLoader imageLoader = new ImageLoader.Builder()
                .imgView(mImageView)
                .url(url)
                .listener(progressListener)
                .build();
        ImageLoaderUtil.getInstance().loadImage(this,imageLoader);
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;

        }
    }

    private void setNightMode() {
        //  获取当前模式
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        //  将是否为夜间模式保存到SharedPreferences
        UserInfoTools.setNightMode(this, currentNightMode == Configuration.UI_MODE_NIGHT_NO);
        //  切换模式
        getDelegate().setDefaultNightMode(currentNightMode == Configuration.UI_MODE_NIGHT_NO ?
                AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
//        UserInfoTools.setChangeNightMode(this,true);
        //  重启Activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
