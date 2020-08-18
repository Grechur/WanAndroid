package com.grechur.wanandroid.ui;

import android.animation.Animator;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.grechur.wanandroid.R;

import yanzhikai.textpath.AsyncTextPathView;
import yanzhikai.textpath.PathAnimatorListener;
import yanzhikai.textpath.SyncTextPathView;

public class SplashActivity extends AppCompatActivity {
    SyncTextPathView stpv_2017;
    AsyncTextPathView atpv_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        stpv_2017 = findViewById(R.id.stpv_2017);
        atpv_1 = findViewById(R.id.atpv_1);
        stpv_2017.startAnimation(0,1);
        atpv_1.startAnimation(0,1);
        stpv_2017.setAnimatorListener(new PathAnimatorListener(){
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
        });
//        CountDownTimer countDownTimer = new CountDownTimer(2100,1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//            }
//
//            @Override
//            public void onFinish() {
//                startActivity(new Intent(SplashActivity.this,MainActivity.class));
//                finish();
//            }
//        };
//        countDownTimer.start();
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
