package com.grechur.wanandroid.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class ActivityAspectj {

    @Before("execution (* android.app.Activity.onCreate(..)) || execution (* android.app.Activity.onResume(..))" +
            "||execution (* android.app.Activity.onPause(..))||execution (* android.app.Activity.onBackPressed(..))" )
    public void onActivityMethod(JoinPoint joinPoint) throws Throwable{
        AopUtil.sendActivityMethodToSDK(joinPoint);
    }

    @Before("execution (* startActivity(..))")
    public void startActivityMetheod(JoinPoint joinPoint) throws Throwable{
        AopUtil.sendActivityMethodToSDK(joinPoint);
    }
}
