package com.grechur.wanandroid.aop;


import android.os.Build;
import android.support.annotation.RequiresApi;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class ViewOnClickListenerAspectj {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    @Before("execution(* android.view.View.OnClickListener.onClick(android.view.View))")
    @Before("execution(* onClick(..))")
    public void onViewClickAOP(final JoinPoint joinPoint) throws Throwable {
        AopUtil.sendTrackEventToSDK(joinPoint);
    }

}
