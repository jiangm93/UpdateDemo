package com.jiangm.update.uptutil;

/**
 * 下载进度监听
 * Created by Administrator on 2018/7/24.
 */

public interface JsDownloadListener {
    void onStartDownload();

    void onProgress(int progress);

    void onFinishDownload();

    void onFail(String errorInfo);


}
