package com.grechur.wanandroid.base;

import com.grechur.wanandroid.contract.UserInfoContract;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;

/**
 * Created by zz on 2018/5/18.
 */

public class BasePresenter<V extends BaseView,M extends BaseModel> {
    // 一个是原始的 View ，一个是代理的 View
    private V mView = null;
    private V mProxyView = null;
    protected M model = null;

    public void attach(V view) {
        this.mView = view;
        // 动态代理
        mProxyView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(),
                view.getClass().getInterfaces(), new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (mView != null) {
                            return method.invoke(mView, args);
                        }
                        return null;
                    }
                });

        // 注入 Model，怎么注入，获取泛型的类型，也就是 M 的 class，利用反射new 一个对象
        try {
            ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class<M> modelClazz = (Class<M>) (parameterizedType.getActualTypeArguments()[1]);
            model = modelClazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void detach() {
        this.mView = null;
    }

    public V getView() {
        return mProxyView;
    }


}
