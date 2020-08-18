package com.grechur.wanandroid.aop;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.grechur.wanandroid.ui.LoginActivity;
import com.grechur.wanandroid.utils.AccountMgr;
import com.grechur.wanandroid.utils.LogUtils;
import com.grechur.wanandroid.utils.ToastUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Parameter;

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
        LogUtils.e(TAG,"checkLogin");
        ToastUtils.show("checkLogin");
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        LogUtils.e(TAG,"methodSignature.getMethod():"+methodSignature.getMethod().toString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Parameter[] parameters = methodSignature.getMethod().getParameters();
            for (Parameter parameter : parameters) {
                LogUtils.e(TAG,"methodSignature.getParameters():"+parameter.toString());
                LogUtils.e(TAG,"methodSignature.getParameters().getModifiers():"+parameter.getModifiers());
                LogUtils.e(TAG,"methodSignature.getParameters().getDeclaringExecutable():"+parameter.getDeclaringExecutable());
            }
        }
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
        LoginCheck loginCheck = methodSignature.getMethod().getAnnotation(LoginCheck.class);
        if(loginCheck!=null){
            Object object = joinPoint.getThis();//有可能是view/activity/fragment
            LogUtils.e(TAG,"joinPoint.getThis():"+object.toString());
            LogUtils.e(TAG,"joinPoint.getTarget():"+joinPoint.getTarget());
            LogUtils.e(TAG,"joinPoint.getKind():"+joinPoint.getKind());
            LogUtils.e(TAG,"joinPoint.getStaticPart():"+joinPoint.getStaticPart());

            Context context = getContext(object);
            LogUtils.e(TAG,"context:"+context);
            if(context!=null){
                AccountMgr accountMgr = new AccountMgr(context);
                String userName = accountMgr.getVal("username");
                LogUtils.e(TAG,"userName"+userName);
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
        LogUtils.e(TAG,"object:"+obj);
        if(obj instanceof Activity) {
            return (Activity)obj;
        }else if(obj instanceof android.app.Fragment){
            android.app.Fragment fragment = (android.app.Fragment) obj;
            return fragment.getActivity();
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
