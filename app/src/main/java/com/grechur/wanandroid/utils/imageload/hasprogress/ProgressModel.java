package com.grechur.wanandroid.utils.imageload.hasprogress;

/**
 * Created by zz on 2018/8/21.
 */

public class ProgressModel {
    private long currentBytes;
    private long contentLength;

    public void setCurrentBytes(long currentBytes) {
        this.currentBytes = currentBytes;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public long getCurrentBytes() {
        return currentBytes;
    }

    public long getContentLength() {
        return contentLength;
    }
}
