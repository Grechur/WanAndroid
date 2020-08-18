package com.grechur.wanandroid.utils.imageload.hasprogress;



import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.Nullable;


import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by zz on 2018/8/20.
 */

public class ProgressResponseBody extends ResponseBody {


    private static final String TAG = "XGlide";

    private BufferedSource bufferedSource;

    private ResponseBody responseBody;

    private ProgressListener listener;
    private static final int UPDATE = 1;
    private MyHandler handler;

    public ProgressResponseBody(String url, ResponseBody responseBody) {
        this.responseBody = responseBody;
        listener = ProgressInterceptor.LISTENER_MAP.get(url);
        handler = new MyHandler();
    }


    @Nullable
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(new ProgressSource(responseBody.source()));
        }
        return bufferedSource;
    }

    private class ProgressSource extends ForwardingSource {

        long totalBytesRead = 0;

        ProgressSource(Source source) {
            super(source);
        }

        @Override
        public long read(Buffer sink, long byteCount) throws IOException {
            long bytesRead = super.read(sink, byteCount);
            long fullLength = responseBody.contentLength();
            if (bytesRead == -1) {
                totalBytesRead = fullLength;
            } else {
                totalBytesRead += bytesRead;
            }
            ProgressModel progressModel = new ProgressModel();
            progressModel.setContentLength(fullLength);
            progressModel.setCurrentBytes(totalBytesRead);
            Message message = Message.obtain();
            message.what = UPDATE;
            message.obj = progressModel;
            handler.sendMessage(message);
            return bytesRead;
        }
    }

    /**
     * 将进度放到主线程中显示
     */
    class MyHandler extends Handler {

        public MyHandler() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE:
                    ProgressModel progressModel = (ProgressModel) msg.obj;
                    //接口返回
                    if (listener!=null)listener.onProgress(progressModel.getCurrentBytes(),progressModel.getContentLength());
                    break;

            }
        }
    }
}
