package com.grechur.wanandroid.aop;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.grechur.wanandroid.ui.LoginActivity;
import com.grechur.wanandroid.utils.AccountMgr;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by zhouzhu on 2018/5/28.
 */
@Aspect
public class SectionAspect {
    public static final String TAG = "SectionAspect";
    /**
     * 找到处理的切点 * *(..)处理所有方法
     */
    @Pointcut("execution(@com.grechur.wanandroid.aop.LoginCheck * *(..))")
    public void checkLoginBehavior(){

    }

    @Around("checkLoginBehavior()")
    public Object checkLogin(ProceedingJoinPoint joinPoint) throws Throwable{
        Log.e(TAG,"checkLogin");
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        LoginCheck loginCheck = methodSignature.getMethod().getAnnotation(LoginCheck.class);
        if(loginCheck!=null){
            Object object = joinPoint.getThis();//有可能是view/activity/fragment
            Context context = getContext(object);
            if(context!=null){
                AccountMgr accountMgr = new AccountMgr(context);
                String userName = accountMgr.getVal("username");
                if(TextUtils.isEmpty(userName)){
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                    return null;
                }
            }

        }

        return joinPoint.proceed();
    }

    private Context getContext(Object obj){
        if(obj instanceof Activity)
        {
            return (Activity)obj;
        }else if(obj instanceof Fragment){
            Fragment fragment = (Fragment) obj;
            return fragment.getActivity();
        }else if(obj instanceof View){
            View view = (View) obj;
            return view.getContext();
        }
        return null;
    }
}
