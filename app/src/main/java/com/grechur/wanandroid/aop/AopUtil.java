package com.grechur.wanandroid.aop;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class AopUtil {
    public static final String TAG = "AopUtil";
    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void sendTrackEventToSDK(JoinPoint joinPoint) throws Throwable{
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Log.e(TAG,"点击方法:"+methodSignature.getMethod().toString());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            Parameter[] parameters = methodSignature.getMethod().getParameters();
//            for (Parameter parameter : parameters) {
//                Log.e(TAG,"methodSignature.getParameters():"+parameter.toString());
//                Log.e(TAG,"methodSignature.getParameters().getName():"+parameter.getName());
//                Log.e(TAG,"methodSignature.getParameters().getDeclaringExecutable():"+parameter.getDeclaringExecutable());
//            }
//        }
        Object object = joinPoint.getThis();
        Log.e(TAG,"事件发生的页面:"+joinPoint.getThis().getClass());
//        Log.e(TAG,"joinPoint.getTarget():"+joinPoint.getTarget());
        Log.e(TAG,"事件的代码路径:"+joinPoint.getSourceLocation());
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if(arg instanceof View) {
                View view = (View) arg;
                Log.e(TAG, "被点击的:" + view);
                Fragment fragment = null;
                if(view.getContext() instanceof FragmentActivity){
                    fragment = getVisibleFragment((FragmentActivity) view.getContext());
                }

                if (fragment != null) {
                    try {
                        Class clazz = fragment.getClass();
                        Field field = clazz.getDeclaredField("mProductId");
                        if(field!=null) {
                            field.setAccessible(true);
                            Object obj = field.get(fragment);
                            Log.e(TAG, "mTitle: " + obj);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String tag = fragment.getTag();
                    if(tag!=null) {
                        String[] strings = tag.split(":");
                        int index = strings.length - 1;
                        Log.e(TAG, "位置下标: " + strings[index]);
                    }
//                Log.e(TAG, "getTargetRequestCode: " +fragment+fragment.getId());
                }

                Log.e(TAG, "点击控件的父类:" + view.getContext());
                int id = view.getId();
//            Log.e(TAG,"id:"+id);
                if (id > 0) {
                    String name = view.getResources().getResourceName(id);
                    Log.e(TAG, "被点击控件的名字:" + name);

                }
            }
        }

    }

    public static Fragment getVisibleFragment(FragmentActivity context){
        FragmentManager fragmentManager = context.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for(Fragment fragment : fragments){
            if(fragment != null && fragment.getUserVisibleHint())
                return fragment;
        }
        return null;
    }

    public static void sendActivityMethodToSDK(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        String name = method.getName();
        Log.e(TAG,"响应的页面:"+joinPoint.getThis().toString());
        Log.e(TAG,"响应的方法:"+name);

        if(name.equals("startActivity")){
            Object[] object = joinPoint.getArgs();
            for (Object o : object) {
                if(o instanceof Intent){
                    Intent intent = (Intent) o;
                    ComponentName componentName = intent.getComponent();
//                    Log.e(TAG, "sendActivityMethodToSDK: "+componentName.getPackageName()+"  "+componentName.getClassName()+"  "+componentName.getShortClassName() );
                    Log.e(TAG,"跳转路径:"+joinPoint.getThis().getClass()+"--->"+componentName.getClassName());
                }
            }
        }

    }
}
