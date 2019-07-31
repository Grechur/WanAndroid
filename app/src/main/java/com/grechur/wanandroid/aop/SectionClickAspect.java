package com.grechur.wanandroid.aop;

import android.util.Log;
import android.view.View;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
@Aspect
public class SectionClickAspect {
    public static final String TAG = "SectionClickAspect";
    @After("execution(* com.grechur.wanandroid.view.HomeFrgAdapter.OnItemViewClickListen.onItemClick(android.view.View,int))")
    public void checkClick(JoinPoint joinPoint)throws Throwable{
        Log.e(TAG,"joinPoint.getThis():"+joinPoint.getThis());
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            Log.e(TAG,"arg:"+arg);
            if(arg instanceof View) {
                View view = (View) arg;
                int id = view.getId();
                Log.e(TAG, "id:" + id);
                if (id > 0) {
                    String name = view.getResources().getResourceName(id);
                    Log.e(TAG, "name:" + name);
                }
            }
        }
    }
}
