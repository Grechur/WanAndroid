package com.grechur.wanandroid.api;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.grechur.wanandroid.R;
import com.grechur.wanandroid.base.BasicResponse;


import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;


public abstract class DefaultObserver <T extends BasicResponse> implements Observer<T> {
//    private Activity activity;
//    //  Activity 是否在执行onStop()时取消订阅
//    private boolean isAddInStop = false;
//
//    public DefaultObserver(Activity activity) {
//        this.activity = activity;
//    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T response) {
        if (response.getCode().equals("0")) {
            onSuccess(response);
        } else {
            onFail(response.getCode(),response.getErrmsg());
        }
    }


    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {     //   HTTP错误
//            onException(ExceptionReason.BAD_NETWORK);
            onFail("-3","服务器异常");
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误
//            onException(CONNECT_ERROR);
            onFail("-1","网络连接失败,请检查网络");
        } else if (e instanceof InterruptedIOException) {   //  连接超时
//            onException(CONNECT_TIMEOUT);
            onFail("-2","连接超时,请稍后再试");
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
//            onException(PARSE_ERROR);
            onFail("-4","解析服务器响应数据失败");
        } else {
//            onException(UNKNOWN_ERROR);
            onFail("-5","未知错误");
        }
    }

    @Override
    public void onComplete() {
    }

    /**
     * 请求成功
     *
     * @param response 服务器返回的数据
     */
    abstract public void onSuccess(T response);

    /**
     * 服务器返回数据，但响应码不为200
     *
     * @param code 服务器返回的错误编码
     * @param msg 服务器返回的错误编码
     */
    protected abstract void onFail(String code, String msg) ;

    /**
     * 请求异常
     *
     * @param reason

    public void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
//                ToastUtils.show(R.string.connect_error, Toast.LENGTH_SHORT);
                onFail("-1",R.string.connect_error);
                break;

            case CONNECT_TIMEOUT:
//                ToastUtils.show(R.string.connect_timeout, Toast.LENGTH_SHORT);
                onFail(-2,R.string.connect_timeout);
                break;

            case BAD_NETWORK:
//                ToastUtils.show(R.string.bad_network, Toast.LENGTH_SHORT);
                onFail(-3,R.string.bad_network);
                break;

            case PARSE_ERROR:
//                ToastUtils.show(R.string.parse_error, Toast.LENGTH_SHORT);
                onFail(-4,R.string.parse_error);
                break;

            case UNKNOWN_ERROR:
            default:
//                ToastUtils.show(R.string.unknown_error, Toast.LENGTH_SHORT);
                onFail(-5,R.string.unknown_error);
                break;
        }
    }
     */
    /**
     * 请求网络失败原因
     */
//    public enum ExceptionReason {
//        /**
//         * 解析数据失败
//         */
//        PARSE_ERROR,
//        /**
//         * 网络问题
//         */
//        BAD_NETWORK,
//        /**
//         * 连接错误
//         */
//        CONNECT_ERROR,
//        /**
//         * 连接超时
//         */
//        CONNECT_TIMEOUT,
//        /**
//         * 未知错误
//         */
//        UNKNOWN_ERROR,
//    }
}
