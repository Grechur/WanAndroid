package com.grechur.wanandroid.aop;

import androidx.fragment.app.Fragment;
import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.lang.reflect.Field;

@Aspect
public class FragmentAspect {
    public static final String TAG = "AopUtil";

    @Before("execution(* android.support.v4.app.Fragment.setUserVisibleHint(..))")
    public void onFragmentMethodBefore(JoinPoint joinPoint) throws Throwable {
        String key = joinPoint.getSignature().toString();
        Log.e(TAG, "onFragmentMethodBefore: " + key+"\n"+joinPoint.getThis());
        Object object = joinPoint.getThis();
        if(object instanceof Fragment){
            Fragment fragment = (Fragment) object;
            try {
                Class clazz = fragment.getClass();
                Field field = clazz.getDeclaredField("mProductId");
                if(field!=null) {
                    field.setAccessible(true);
                    Object obj = field.get(fragment);
                    Log.e(TAG, "mTitle: " + obj);
                }
            }catch (Exception e){

            }
            String tag = fragment.getTag();
            if(tag!=null) {
                String[] strings = tag.split(":");
                int index = strings.length - 1;
                Log.e(TAG, "tag: " + strings[index]);
            }
            Log.e(TAG, "joinPoint.getStaticPart(): " +joinPoint.getStaticPart());;
            Log.e(TAG, "getTargetRequestCode: " +fragment+fragment.getId());
        }
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            Log.e(TAG, "arg: " +arg);
        }
    }



}
