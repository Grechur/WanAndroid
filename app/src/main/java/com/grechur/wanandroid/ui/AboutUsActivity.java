package com.grechur.wanandroid.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.grechur.wanandroid.R;
import com.grechur.wanandroid.contract.UserInfoContract;
import com.grechur.wanandroid.utils.UserInfoTools;

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

    Unbinder unbinder;
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
                if(isChecked){
                    setNightMode();
                }
            }
        });
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
        UserInfoTools.setChangeNightMode(this,true);
        //  重启Activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
